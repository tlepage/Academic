__author__ = 'tomlepage'
# Write a procedure, remove_tags, that takes as input a string and returns
# a list of words, in order, with the tags removed. Tags are defined to be
# strings surrounded by < >. Words are separated by whitespace or tags.
# You may assume the input does not include any unclosed tags, that is,
# there will be no '<' without a following '>'.
def remove_tags(s):
    list=[]
    if '<' not in s:
        return s.split()
    while s != '':
        if '<' not in s:
            list = list+s.split()
            return list
        elif s[0] == '<':
            index = s.find('>')
            s = s[index+1:]
        else:
            index = s.find('<')
            temp = s[0:index]
            s = s[index:]
            list = list + (temp.split())
    return list

print remove_tags('''<h1>Title</h1><p>This is a
                    <a href="http://www.udacity.com">link</a>.<p>''')
#>>> ['Title','This','is','a','link','.']

print remove_tags('''<table cellpadding='3'>
                     <tr><td>Hello</td><td>World!</td></tr>
                     </table>''')
#>>> ['Hello','World!']

print remove_tags("<hello><goodbye>")
#>>> []

print remove_tags("This is plain text.")
#>>> ['This', 'is', 'plain', 'text.']
