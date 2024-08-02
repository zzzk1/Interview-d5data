import json
from datasets import load_dataset


def load_questions(file_path):
    """加载包含问题和 ID 的 JSON 文件"""
    with open(file_path, 'r') as f:
        questions = json.load(f)
    return questions


def find_answer(text, question):
    """从文本中寻找答案的简单示例"""
    # 这里只是一个示例，实际可能需要更复杂的 NLP 方法来提取答案
    # 这里只是找到问题中的关键字在文本中出现的地方
    # 真实场景中可能需要使用问答模型来提取答案
    if question in text:
        return text.split(question, 1)[-1].split('\n')[0].strip()
    return "Answer not found"


def process_questions(questions, dataset):
    """根据问题的 ID 从数据集中找到相应的条目并提取答案"""
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
    return results


# 加载数据集
dataset = load_dataset("AdaptLLM/finance-tasks", "ConvFinQA")

# 加载问题
questions = load_questions("questions.json")

# 处理问题并提取答案
answers_list = process_questions(questions, dataset)

# 将结果保存为 JSON 文件
output_file = "answers.json"
with open(output_file, 'w') as f:
    json.dump(answers_list, f, indent=4)

print(f"Results have been saved to {output_file}")
