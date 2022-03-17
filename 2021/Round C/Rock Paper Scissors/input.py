import random

Gs = random.sample(range(5, 96), 50)

T = 200
print(T)

X = 16400
print(X)

for G in Gs:
    W = 10*G
    for E in [W, W//2, W//10, 0]:
        print(W, E)
