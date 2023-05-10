import tensorflow as tf 

print(tf.config.list_physical_devices('GPU'))
print(tf.__version__)

import torch

gpu_available = torch.cuda.get_device_name(0)
print(gpu_available)
print(torch.cuda.is_available())