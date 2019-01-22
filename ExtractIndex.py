# -*- coding: utf-8 -*

#提取 ## XX ## 作索引 [XX](#XX), 直接打印出来

import re

fileName = 'C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\operator\README.md'

textFile = open(fileName, 'r')

indexRegex = re.compile(r'^## (.*) ##$')

for line in textFile.readlines():
    if indexRegex.match(line):
        
        mo = indexRegex.search(line)

        # line
        print '[%s](#%s)\n'%(mo.group(1), mo.group(1).lower())
