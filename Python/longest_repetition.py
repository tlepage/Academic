__author__ = 'tomlepage'
# Define a procedure, longest_repetition, that takes as input a
# list, and returns the element in the list that has the most
# consecutive repetitions. If there are multiple elements that
# have the same number of longest repetitions, the result should
# be the one that appears first. If the input list is empty,
# it should return None.

def longest_repetition(i):
    count = 0
    largest_count = 0
    curr = None
    largest = None
    for e in i:
        if curr == None:
            curr = e
            largest = e
            count = 1
        else:
            if e == curr:
                count += 1
                if count > largest_count:
                    largest_count = count
                    largest = curr
            else:
                curr = e
                count = 1

    return largest





#For example,

print longest_repetition([1, 2, 2, 3, 3, 3, 2, 2, 1])
# 3

print longest_repetition(['a', 'b', 'b', 'b', 'c', 'd', 'd', 'd'])
# b

print longest_repetition([1,2,3,4,5])
# 1

print longest_repetition([])
# None

print longest_repetition([2, 2, 3, 3, 3])

