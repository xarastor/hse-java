from sklearn.datasets import load_digits
import matplotlib.pyplot as plt


digits = load_digits()
print(digits.keys())
print(digits['images'].shape)
print(digits['target_names'].shape)

plt.gray()
for i in range(10):
    print(digits['target'][i])
    plt.matshow(digits.images[i])
    plt.show()
