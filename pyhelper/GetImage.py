import requests
import time
import os
def get_img_url(keyword):
    """发送请求，获取接口中的数据"""
    # 接口链接
    url = 'https://image.baidu.com/search/acjson?'
    # 请求头模拟浏览器
    headers = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36'}
    # 构造网页的params表单
    params = {
        'tn': 'resultjson_com',
        'logid': '6918515619491695441',
        'ipn': 'rj',
        'ct': '201326592',
        'is': '',
        'fp': 'result',
        'queryWord': f'{keyword}',
        'word': f'{keyword}',
        'cl': '2',
        'lm': '-1',
        'ie': 'utf-8',
        'oe': 'utf-8',
        'adpicid': '',
        'st': '-1',
        'z': '',
        'ic': '',
        'hd': '',
        'latest': '',
        'copyright': '',
        's': '',
        'se': '',
        'tab': '',
        'width': '',
        'height': '',
        'face': '0',
        'istype': '2',
        'qc': '',
        'nc': '1',
        'fr': '',
        'expermode': '',
        'force': '',
        'cg': 'girl',
        'pn': 1,
        'rn': '30',
        'gsm': '1e',
    }
    # 携带请求头和params表达发送请求
    response  = requests.get(url=url, headers=headers, params=params)
    # 设置编码格式
    response.encoding = 'utf-8'
    # 转换为json
    json_dict = response.json()
    # 定位到30个图片上一层
    data_list = json_dict['data']
    # 删除列表中最后一个空值
    del data_list[-1]
    # 用于存储图片链接的列表
    img_url_list = []
    cnt=0
    for i in data_list:
        img_url = i['thumbURL']
        # 打印一下图片链接
        print(img_url)
        img_url_list.append(img_url)
        cnt=cnt+1
        if cnt>=2 : break
    # 返回图片列表
    return img_url_list
def get_down_img(img_url_list,keyword,province,type):
    # 在当前路径下生成存储图片的文件夹
    # os.mkdir("Desktop/py_test/"+keyword)
    # 定义图片编号
    n=1
    for img_url in img_url_list:
        headers = {
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36'}
        img_data = requests.get(url=img_url, headers=headers).content
        # 拼接图片存放地址和名字
        name=province+"_"+type+"_"+keyword
        img_path = './Desktop/py_test/image/'+name+'_'+str(n)+'.jpg'
        # 将图片写入指定位置
        with open(img_path, 'wb') as f:
            f.write(img_data)
        n+=1
        # 图片编号递增
# os.mkdir("Desktop/py_test/image")
# get_down_img(get_img_url("故宫"),"故宫","beijing","景点")
allpro=["henan","hebei","shanxi","liaoning","jilin"]
allpro=["heilongjiang"] # 库木勒节
allpro=["jiangsu"] # 刀鱼卤面
allpro=["zhejiang","anhui"] # 六安酱鸭
allpro=["fujian","jiangxi","shandong"] # 胡集书会
allpro=["hubei","hunan"] # 六月六山歌节
allpro=["guangdong","hainan","sichuan","guizhou","yunnan","shaanxi"] # 荞面饸饹
allpro=["gansu","qinghai"] # 波波会
allpro=["ningxia","xinjiang","xizang"] # 尚白习俗
allpro=["guangxi","neimenggu","taiwan"] # 棺材板
allpro=["xianggang","aomen","beijing","tianjin"] # 天津麻花
allpro=["shanghai","chongqing"] # 重庆酸辣粉
allpro=["henan","hebei","shanxi","liaoning","jilin","heilongjiang","jiangsu","zhejiang","anhui","fujian","jiangxi","shandong","hubei","hunan","guangdong","hainan","sichuan","guizhou","yunnan","shaanxi","gansu","qinghai","ningxia","xinjiang","xizang","guangxi","neimenggu","taiwan","xianggang","aomen","beijing","tianjin","shanghai","chongqing"]

for pro in allpro :
    f=open('./Desktop/py_test/data/'+pro+'22.txt',mode='r',encoding='utf-8')
    g=open('./Desktop/py_test/list.txt',mode='w+',encoding='utf-8')
    time.sleep(1)
    g.write()
    for type in ["景点","美食","民俗"] :
        g.write(type+'：')
        while True :
            s=f.readline()
            if s.count("$")!=0 : break
            if len(s)<5 : continue
            st=s.find('.')
            en=s.find('：')
            g.write(s[st+1:en])
            g.write(' ')
            # print(pro,type,s[st+1:en])
            # time.sleep(1)
            # get_down_img(get_img_url(s[st+1:en]),s[st+1:en],pro,type)
        g.write('\n')