import json
from datasets import load_dataset
import re

def clean_question(sentence):
    """清理疑问句前后的多余内容"""
    # 删除前后的多余字符，如点、换行符、连字符和非字母数字字符
    sentence = sentence.strip()
    sentence = re.sub(r'^\d+[\s\.\n-]*', '', sentence)  # 去掉开头的数字和空白符
    return sentence

def split_sentences(text):
    """将文本分割成句子"""
    # 句子分隔符列表
    sentence_endings = ['.', '?', '!']
    sentences = []
    start = 0
    for i, char in enumerate(text):
        if char in sentence_endings:
            # 如果找到句子结束符，提取句子并保存
            sentences.append(text[start:i+1].strip())
            start = i + 1
    # 添加最后一句
    if start < len(text):
        sentences.append(text[start:].strip())
    return sentences

def extract_questions(text):
    """从文本中提取疑问句"""
    sentences = split_sentences(text)
    questions = [clean_question(sentence) for sentence in sentences if sentence.endswith('?')]
    return questions

def process_dataset(dataset):
    """从数据集中提取疑问句"""
    results = []
    for split in dataset.keys():  # 遍历数据集中的每个部分
        print(f"Processing {split} split...")
        for entry in dataset[split]:
            text = entry.get("input", "")
            questions = extract_questions(text)
            for question in questions:
                results.append({
                    "id": entry.get("id", ""),
                    "question": question
                })
    return results

# 加载数据集
dataset = load_dataset("AdaptLLM/finance-tasks", "ConvFinQA")

# 处理数据集并提取疑问句
questions_list = process_dataset(dataset)

# 将结果保存为 JSON 文件
output_file = "questions.json"
with open(output_file, 'w') as f:
    json.dump(questions_list, f, indent=4)

print(f"Results have been saved to {output_file}")
