__author__ = 'tomlepage'
# Define a procedure, deep_reverse, that takes as input a list,
# and returns a new list that is the deep reverse of the input list.
# This means it reverses all the elements in the list, and if any
# of those elements are lists themselves, reverses all the elements
# in the inner list, all the way down.

# Note: The procedure must not change the input list.


def is_list(p):
    return isinstance(p, list)

def reverse(p):
    revlist = []
    for i in range(len(p) - 1, -1, -1):
        revlist.append(p[i])

    return revlist

def deep_reverse(p):
    reverse = []

    if is_list(p):
        i = len(p) - 1
        while i > -1:
            if is_list(p[i]):
                reverse.append(deep_reverse(p[i]))
            else:
                reverse.append(p[i])

            i -= 1
    else:
        reverse.append(p)

    return reverse


#For example,

p = [1, [2, 3, [4, [5, 6]]]]
print deep_reverse(p)
#>>> [[[[6, 5], 4], 3, 2], 1]
print p
#>>> [1, [2, 3, [4, [5, 6]]]]

q =  [1, [2,3], 4, [5,6]]
print deep_reverse(q)
#>>> [ [6,5], 4, [3, 2], 1]
print q
#>>> [1, [2,3], 4, [5,6]]
