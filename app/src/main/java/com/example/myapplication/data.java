package com.example.myapplication;

public class data {
    String[][][] savedata = new String[3][8][14];
    public void start()
    {
        for(int i = 0; i<3; i++)
            for(int j = 0; j<8; j++)
                for(int k = 0; k<14; k++)
                {
                    savedata[i][j][k] = "null";
                }
    }
    public void data(String fcstDate, String fcstTime, String category, String fcstValue, String Dday, String Pday, String PPday){

        if(fcstDate.equals(Dday))
        {
            switch (fcstTime){
                case "0900":
                    Wdata(category,fcstValue, 0, 3);
                    break;
                case "1200":
                    Wdata(category,fcstValue, 0, 4);
                    break;
                case "1500":
                    Wdata(category,fcstValue, 0, 5);
                    break;
                case "1800":
                    Wdata(category,fcstValue, 0, 6);
                    break;
                case "2100":
                    Wdata(category,fcstValue, 0, 7);
                    break;

            }
        }
        else if(fcstDate.equals(Pday))
        {
            switch (fcstTime){
                case "0000":
                    Wdata(category,fcstValue, 1, 0);
                    break;
                case "0300":
                    Wdata(category,fcstValue, 1, 1);
                    break;
                case "0600":
                    Wdata(category,fcstValue, 1, 2);
                    break;
                case "0900":
                    Wdata(category,fcstValue, 1, 3);
                    break;
                case "1200":
                    Wdata(category,fcstValue, 1, 4);
                    break;
                case "1500":
                    Wdata(category,fcstValue, 1, 5);
                    break;
                case "1800":
                    Wdata(category,fcstValue, 1, 6);
                    break;
                case "2100":
                    Wdata(category,fcstValue, 1, 7);
                    break;

            }
        }
        else if(fcstDate.equals(PPday))
        {
            switch (fcstTime){
                case "0000":
                    Wdata(category,fcstValue, 2, 0);
                    break;
                case "0300":
                    Wdata(category,fcstValue, 2, 1);
                    break;
                case "0600":
                    Wdata(category,fcstValue, 2, 2);
                    break;
                case "0900":
                    Wdata(category,fcstValue, 2, 3);
                    break;
                case "1200":
                    Wdata(category,fcstValue, 2, 4);
                    break;
                case "1500":
                    Wdata(category,fcstValue, 2, 5);
                    break;
                case "1800":
                    Wdata(category,fcstValue, 2, 6);
                    break;
                case "2100":
                    Wdata(category,fcstValue, 2, 7);
                    break;

            }
        }
        else
        {
            Wdata(category,fcstValue, 0, 0);
        }
    }
    public void Wdata(String category, String fcstValue, int i, int j){
        switch (category) {
            case "POP": //����Ȯ��
                savedata[i][j][0] = fcstValue;
                break;
            case "PTY": //�������� 1���� 1�� 2��/�� 3�� 4�ҳ��� 5����� 6�����/������ 7������
                savedata[i][j][1] = fcstValue;
                break;
            case "R06": //6�ð� ������
                savedata[i][j][2] = fcstValue;
                break;
            case "REH": //����
                savedata[i][j][3] = fcstValue;
                break;
            case "S06": //6�ð� ������
                savedata[i][j][4] = fcstValue;
                break;
            case "SKY": //�ϴû��� 1���� 3�������� 4�帲
                savedata[i][j][5] = fcstValue;
                break;
            case "T3H": //3�ð� ���
                savedata[i][j][6] = fcstValue;
                break;
            case "TMN": //��ħ �������
                savedata[i][j][7] = fcstValue;
                break;
            case "TMX": //�� �ְ���
                savedata[i][j][8] = fcstValue;
                break;
            case "UUU": //ǳ��(��������) ��(+) ��(-)
                savedata[i][j][9] = fcstValue;
                break;
            case "VVV": //ǳ��(���ϼ���) ��(+) ��(-)
                savedata[i][j][10] = fcstValue;
                break;
            case "WAV": //�İ�
                savedata[i][j][11] = fcstValue;
                break;
            case "VEC": //ǳ��
                savedata[i][j][12] = fcstValue;
                break;
            case "WSD": //ǳ��
                savedata[i][j][13] = fcstValue;
                break;
        }
    }
}
