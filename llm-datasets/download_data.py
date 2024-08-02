from datasets import load_dataset

# load dataset.
ds = load_dataset("AdaptLLM/finance-tasks", "ConvFinQA")

# save as json format.
ds['test'].to_json('test_data.json')


