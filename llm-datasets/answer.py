import json
import time
from datasets import load_dataset
from transformers import AutoTokenizer, AutoModelForQuestionAnswering
import torch

# 加载预训练的问答模型和tokenizer
model_name = "bert-large-uncased-whole-word-masking-finetuned-squad"
tokenizer = AutoTokenizer.from_pretrained(model_name)
model = AutoModelForQuestionAnswering.from_pretrained(model_name)


def log_time(message, start_time, log_file="time_log.txt"):
    """记录耗时到日志文件"""
    end_time = time.time()
    elapsed_time = end_time - start_time
    with open(log_file, 'a') as log:
        log.write(f"{message}: {elapsed_time:.2f} seconds\n")


def load_questions(file_path):
    """加载包含问题和 ID 的 JSON 文件"""
    start_time = time.time()
    try:
        with open(file_path, 'r') as f:
            questions = json.load(f)
        log_time("Loading questions", start_time)
        return questions
    except FileNotFoundError:
        print(f"Error: File {file_path} not found.")
        log_time("Loading questions failed (file not found)", start_time)
        return []
    except json.JSONDecodeError:
        print("Error: Failed to decode JSON.")
        log_time("Loading questions failed (JSON decode error)", start_time)
        return []


def find_answer(text, question):
    """使用预训练的问答模型从文本中寻找答案"""
    start_time = time.time()
    inputs = tokenizer.encode_plus(question, text, return_tensors="pt", max_length=512, truncation=True)
    input_ids = inputs["input_ids"].tolist()[0]

    outputs = model(**inputs)
    answer_start_scores = outputs.start_logits
    answer_end_scores = outputs.end_logits

    answer_start = torch.argmax(answer_start_scores)
    answer_end = torch.argmax(answer_end_scores) + 1

    answer = tokenizer.convert_tokens_to_string(tokenizer.convert_ids_to_tokens(input_ids[answer_start:answer_end]))
    log_time("Finding answer", start_time)
    return answer.strip()


def process_questions(questions, dataset):
    """根据问题的 ID 从数据集中找到相应的条目并提取答案"""
    start_time = time.time()
    results = []
    for question_entry in questions:
        question_id = question_entry["id"]
        question_text = question_entry["question"]

        # 查找对应的数据集条目
        for split in dataset.keys():
            for entry in dataset[split]:
                if entry.get("id") == question_id:
                    text = entry.get("input", "")
                    answer = find_answer(text, question_text)
                    results.append({
                        "id": question_id,
                        "question": question_text,
                        "answer": answer
                    })
                    break
    log_time("Processing questions", start_time)
    return results


# 加载数据集
dataset_start_time = time.time()
try:
    dataset = load_dataset("AdaptLLM/finance-tasks", "ConvFinQA")
    log_time("Loading dataset", dataset_start_time)
except Exception as e:
    print(f"Error loading dataset: {e}")
    log_time("Loading dataset failed", dataset_start_time)
    dataset = {}

# 加载问题
questions = load_questions("questions.json")

# 处理问题并提取答案
if questions and dataset:
    answers_start_time = time.time()
    answers_list = process_questions(questions, dataset)
    log_time("Processing answers", answers_start_time)

    # 将结果保存为 JSON 文件
    save_start_time = time.time()
    output_file = "answers.json"
    try:
        with open(output_file, 'w') as f:
            json.dump(answers_list, f, indent=4)
        print(f"Results have been saved to {output_file}")
        log_time("Saving answers", save_start_time)
    except Exception as e:
        print(f"Error writing to file {output_file}: {e}")
        log_time("Saving answers failed", save_start_time)
else:
    print("Processing aborted due to previous errors.")
