import requests
import json
import pandas as pd
AK="XXX"
# AK="xHDckfO9mtIfmprHmu2zDn0GBMw0UzeL"
def getPosition(address): 
    url = "http://api.map.baidu.com/place/v2/search?query={}&region=全国&output=json&ak={}".format(address,AK)
    res = requests.get(url) 
    json_data = json.loads(res.text) 
    if json_data['status'] == 0: 
        lat = json_data["results"][0]["location"]["lat"] # 纬度 
        lng = json_data["results"][0]["location"]["lng"] # 经度 
    else: 
        print("[ERROR] Can not find {}.".format(address)) 
        return "0,0", json_data["status"] 
    return str(lat) + "," + str(lng), json_data["status"]

def getdistance(start, end): 
    url = "http://api.map.baidu.com/routematrix/v2/driving?output=json&origins={}&destinations={}&ak={}".format( start, end,AK)
    res = requests.get(url)
    content = res.content 
    jsonv = json.loads(str(content, "utf-8")) 
    dist = jsonv["result"][0]["duration"]["value"] 
    return dist

def calcdistance(startName, endName): 
    start, status1 = getPosition(startName) 
    end, status2 = getPosition(endName) 
    if status1 == 0 and status2 == 0: 
        return getdistance(start, end) 
    else:
        return -1

if __name__=="__main__":
    # print(getdistance('28.68169,115.915421','36.674857,117.027442'))
    f=open("C:\\Users\\13456\\Desktop\\py_test\\position.txt","r", encoding="utf-8")
    g=open("C:\\Users\\13456\\Desktop\\py_test\\all_time_driving.txt","w", encoding="utf-8")
    # h=open("C:\\Users\\13456\\Desktop\\py_test\\position.txt","w",encoding="utf-8")
    s=f.readline()
    name,pos=[],[]
    while s.strip()!="" :
        x,y=s.strip().split(':\'')
        name.append(x)
        pos.append(y)
        s=f.readline()
        # name.append(s)
    res=[[] for _ in range(40)]
    n=len(name)
    for i in range(n) :
        for j in range(n) :
            if i==j :
                res[i].append(0)
                continue
            x=getdistance(pos[i],pos[j])//3600
            tmp=name[i]+' '+name[j]+' '+str(x)
            g.write(tmp+'\n')
            # res[i].append(getdistance(pos[i],pos[j]))
            print(name[i],name[j],x)
        # break
        # print(name[i]+":",end='')
        # print(getPosition(name[i]))
    # alldis=[]
    # for i in range(n) :
    #     for j in range(n) :
    #         tmp=name[i]+' '+name[j]+' '+str(res[i][j]//1000)
    #         alldis.append(tmp)
    #         # print(name[i],name[j],res[i][j]//1000)
    #     break
    # for i in range(n) :
    #     for j in range(n) :
    #         if i==j : continue
    #         print(name[i],name[j])
    #         res.append(name[i]+"->"+name[j]+"距离为"+str(calcdistance(name[i],name[j])/1000)+"km")
    # for i in res :
    # g.write(i+'\n')