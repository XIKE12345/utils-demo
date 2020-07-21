import pandas as pd
import sys

old_file_path = sys.argv[1]
new_file_path = sys.argv[2]

df = pd.read_excel(old_file_path, 0, header=0, index_col=0)
# 获得矩阵的转置
df_T = df.T
df_T.to_excel(new_file_path, sheet_name='单孔')

