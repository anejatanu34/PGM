import sys
import re

sym_tags = {
    '?':'.',
    '"':'\'\'',
    '\'':'\'\'',
    '(':'(',
    ')':')',
    '...':':',
    '&':'CC',
    '&lt':'CC',
    '&lt;':'CC',
    '&amp;':'CC',
    '+':'SYM',
    '|':':',
    '=':'SYM'
    }
stopwords_arr=["a","able","about","across","after","all","almost","also","am","among","an","and","any","are","as","at","be","because","been","but","by","can","cannot","could","dear","did","do","does","either","else","ever","every","for","from","get","got","had","has","have","he","her","hers","him","his","how","however","i","if","in","into","is","it","its","just","least","let","like","likely","may","me","might","most","must","my","neither","no","nor","not","of","off","often","on","only","or","other","our","own","rather","said","say","says","she","should","since","so","some","than","that","the","their","them","then","there","these","they","this","tis","to","too","twas","us","wants","was","we","were","what","when","where","which","while","who","whom","why","will","with","would","yet","you","your"]
emoticons=[':)', '<3', '^^', '<3', '-_-', ':)', '<3', ';-)', ':)', ':)', ':)', ':)', '<3', ':)', '\\m/', ':-P', ':)', ':)', '\xe2\x99\xa5', '(:', '-____-', '<3', ':)', '\xe2\x99\xa5', '<3', '(:', ':)', '-_-', ';)', ':)', '\xe2\x99\xa5', '\xe2\x99\xa5', ')', ':)', '\xc2\xaf\\_(\xe3\x83\x84)_/\xc2\xaf', ':O', ':]', ':)', 'P', '^_^', ':P', ':D', '<3', ':', ')', ';-p', ':-/', ';)', '=D', ':)', '(', 'Y', ')', '-__-', ':/', ':(', ':)', ';)', '\xe2\x98\x83', ':)', '-_-', '(:', '=]', '===}}', '<3', ':)', ':)', ':))', ':-)', ':))', ":')", ':)', ';', '3', ';)', ':D', ':(', '(', 'y', ')', '>', ':', 'I', ':)', ':(', '<3', ':', '{', 'o_o', ':)', ';)', '>', '_', '>', '<3', 'xD', ':/', '<333', '&hearts', ':D', 'o_O', '=[[', '\xe2\x99\xa5\xe2\x99\xa1\xe2\x99\xa5', ';)', "\xe2\x80\x8b(\xcb\x87_\xcb\x87'!l", ')', '(', '\xe2\x8c\xa3\xcc\x81_\xe2\x8c\xa3\xcc\x80)', ':)', ':)', ':)', ';)', '<333', 'xDD', 'D:', '-___-', ':/', ':)', ':D', '\xe2\x98\xba', '#k', ':(', ':(', ':)', ':(', '.', '__', ".'", ';)', ':-P', ':(', '-_-', '(', 'Y', ')', 'xD', 'XD', '..:', '(', '(\xc2\xac_\xc2\xac', '"', ')', 'O_o', ':D', ':)', '(=', '-_-', 'XD', ':)', '\xe2\x99\xa5\xe2\x99\xa5\xe2\x99\xa5', '=)', 'X', ')', '(:', '/', ':', ';)', ':', 'S', '\xe2\x99\xa5', ';)', ':D', '-_-', ':P', ';)', ':)', ';)', ':/', ':((', '=)', ':)', '<3', '<3', ':D', ':)', ':)', ':)', '<3', ';(', ':P', ':/', '<33', ':)', '-_-', '\xe0\xb2\xa0\xe2\x97\xa1\xe0\xb2\xa0', ':)', ':', '0', '(', '-_-', '=)', ':p', ':(', ':)', '=)', ':-\\', ':', '3', ';-)', ':)']
def tag_token(token):
    pos = None
    if token in sym_tags:
        pos = sym_tags[token]
    elif token[0] == '@' and len(token) > 1:
        pos = 'usr'
    elif token.lower() == 'rt':
        pos = 'rt'
    elif token[0] == '#':
        pos = 'ht'
    elif token[0:7] == 'http://':
        pos = 'url'
    return pos

def get_features(token):
    ltoken=token.lower()
    ftr_lst = []
    pos=tag_token(ltoken)
    ##f1

    if pos:
        ftr_lst.append('SYMBOL_REGX=' + str(pos))
    #### if usr,rt,ht,url dont add extra features just return it 
    ##f2 
    if pos in ['usr', 'rt', 'ht', 'url']:
        return ['SYMBOL_REGX=' + str(pos)]
    #### f3 ######
    if len(token)>1 and re.match('^[A-Z]*$', token):
        ftr_lst.append('ALL_CAPS')
    if re.match('[A-Z]', token[0]):
        ftr_lst.append('IS_CAPITALIZED')
    if re.match('.*[0-9].*', token):
        ftr_lst.append('IS_NUM')
    if re.match(r'[0-9]', token):
        ftr_lst.append('SINGLEDIGIT')
    if re.match(r'[0-9][0-9]', token):
        ftr_lst.append('DOUBLEDIGIT')
    if re.match(r'.*-.*', token):
        ftr_lst.append('HASDASH')
    if re.match(r'[.,;:?!-+\'"]', token):
        ftr_lst.append('PUNCTUATION')
    if token in stopwords_arr:
        ftr_lst.append('STOP_WORD')
    if token in emoticons:
        ftr_lst.append('EMOTICON')
    if ltoken[-2:]=="ed":
        ftr_lst.append('END=ed')
    if ltoken[-2:]=="ly":
        ftr_lst.append('END=ly')
    if len(ltoken) >= 4:
        # Get prefixes
        for i in range(1, 5):
                if i <= len(ltoken):
                    ftr_lst.append('PREFIX=' + ltoken[:i])
        # Get suffixes                            
        for i in range(1, 5):
            if i <= len(ltoken):
                ftr_lst.append('SUFFIX=' + ltoken[-1*i:])
    if ltoken[-3:]=="ing":
        ftr_lst.append('END=ing')
    if ltoken[-2:]=="es":
        ftr_lst.append('END=es')
    if ltoken[-2:]=="ss":
        ftr_lst.append('END=ss')
    return ftr_lst
######################################### Main Func  ###################
w=open(sys.argv[2],'w')
with open(sys.argv[1]) as r:
    for line in r:
        if line.strip():
            #token=line.split("\n")[0]
            token =line.split("\n")[0]
            # ltoken=token.lower()
            ftr_lst=get_features(token)
            ftr_str=""
            for i in range (len(ftr_lst)):
                ftr_str=ftr_str+ftr_lst[i]+" "
            # ftr_str=ftr_str+ftr_lst[len(ftr_lst)-1]  #taking care of a space syntax
            if (len(ftr_lst)>0):
                line_w=token+" "+ftr_str
                # print(line_w)
                w.write(line_w[:-1]+"\n")
            else:
                w.write(line)
        else:
            w.write("\n")
w.close();

