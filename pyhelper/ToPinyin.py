import pohan
from pypinyin import Style
f=open("C:\\Users\\13456\\Desktop\\py_test\\cities.txt","r", encoding="utf-8")
g=open("C:\\Users\\13456\\Desktop\\py_test\\cities_name.txt","w", encoding="utf-8")
s=f.readline()
while s.strip()!="" :
    y=pohan.pinyin.han2pinyin(s,style=Style.NORMAL)
    s=s.strip()
    print(s,end=' ')
    for i in y :
        if i[0]!='\n' :
            print(i[0],end='')
    print()
    s=f.readline()