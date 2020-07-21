import pandas as pd

def excelTranspose(org_file_path, new_file_path):

    df = pd.read_excel(org_file_path, 0, header=0, index_col=0)
    # 获得矩阵的转置
    df_T = df.T
    df_T.to_excel(new_file_path, sheet_name='单孔')
    return "sssssss"