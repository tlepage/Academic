__author__ = 'tomlepage'
#  make_converter(match, replacement)
#     Takes as input two strings and returns a converter. It doesn't have
#     to make a specific type of thing. Itcan
#     return anything you would find useful in apply_converter.
#  apply_converter(converter, string)
#     Takes as input a converter (produced by make_converter), and
#     a string, and returns the result of applying the converter to the
#     input string. This replaces all occurrences of the match used to
#     build the converter, with the replacement.  It keeps doing
#     replacements until there are no more opportunities for replacements.


def make_converter(match, replacement):
    return [match, replacement]


def apply_converter(converter, string):
    while True:
        if converter[0] in string:
            idx = string.find(converter[0])
            end_idx = idx + len(converter[0])
            string = string[:idx] + converter[1] + string[end_idx:]
        else:
            return string

# For example,

c1 = make_converter('aa', 'a')
print apply_converter(c1, 'aaaa')
#>>> a

c = make_converter('aba', 'b')
print apply_converter(c, 'aaaaaabaaaaa')
#>>> ab

c2 = make_converter('&', 'and')
print apply_converter(c2, 'The cat & the dog')

# Note that this process is not guaranteed to terminate for all inputs
# (for example, apply_converter(make_converter('a', 'aa'), 'a') would
# run forever).

