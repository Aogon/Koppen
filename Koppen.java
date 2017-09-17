import java.io.*;
public class Koppen {
  public static void main(String args[]) {
    try {
      File f = new File(args[0]);
      BufferedReader br = new BufferedReader(new FileReader(f));
      String[][] data = new String[2][12];
      String line = br.readLine();
      String climate = null;
      for (int row = 0; line != null; row++) {
        data[row] = line.split(",", 0);
        line = br.readLine();
      }
      br.close();
      double[][] doubleData = new double[2][12];
      for (int i = 0; i < 2; i++) {
        for (int j = 0; j < 12; j++) {
          doubleData[i][j] = Double.valueOf(data[i][j]).doubleValue();
        }
      }

     double high_temp = doubleData[0][0];
     int high_temp_month = 1;
     for (int i = 0; i < 12; i++) {
       if (high_temp < doubleData[0][i]) {
         high_temp_month = i + 1;
       }
       high_temp = Math.max(high_temp, doubleData[0][i]);
     }

     System.out.println("最暖月：" + high_temp_month + "月");
     System.out.println("最暖月平均気温：" + high_temp + "℃");

     double low_temp = doubleData[0][0];
     int low_temp_month = 1;
     for (int i = 0; i < 12; i++) {
       if (low_temp > doubleData[0][i]) {
         low_temp_month = i + 1;
       }
       low_temp = Math.min(low_temp, doubleData[0][i]);
     }

     System.out.println("最寒月：" + low_temp_month + "月");
     System.out.println("最寒月平均気温：" + low_temp + "℃");


     double ave_temp = 0;
     double year_temp = 0;
     for (int i = 0; i < 12; i++) {
       year_temp = year_temp + doubleData[0][i];
     }
     ave_temp = year_temp / 12;
     System.out.println("年平均気温：" + ave_temp + "℃");



     double max_rain = doubleData[1][0];
     int max_rain_month = 1;
     for (int i = 0; i < 12; i++) {
       if (max_rain < doubleData[1][i]) {
         max_rain_month = i + 1;
       }
       max_rain = Math.max(max_rain,doubleData[1][i]);
     }
     System.out.println("最多雨月：" + max_rain_month + "月");
     System.out.println("最多雨月降水量：" + max_rain + "mm");


     double min_rain = doubleData[1][0];
     int min_rain_month = 1;
     for (int i = 0; i < 12; i++) {
       if (min_rain > doubleData[1][i]) {
         min_rain_month = i + 1;
       }
       min_rain = Math.min(min_rain, doubleData[1][i]);
     }
     System.out.println("最少雨月：" + min_rain_month + "月");
     System.out.println("最少雨月降水量：" + min_rain + "mm");


     double year_rain = 0;
     for (int i = 0; i < 12; i++) {
       year_rain = year_rain + doubleData[1][i];
     }
     System.out.println("年降水量" + year_rain + "mm");

     if (high_temp >= 10) {
       String dry_season = null;
       double dry_limit = 0;
       if ((doubleData[0][max_rain_month - 1] >= doubleData[0][min_rain_month - 1])
                && (max_rain > min_rain * 10)) {
         dry_season = "w";
         dry_limit = 20 * (ave_temp + 14);
       } else if ((doubleData[0][max_rain_month - 1] < doubleData[0][min_rain_month - 1])
                && (max_rain > min_rain * 3)) {
         dry_season = "s";
         dry_limit = 20 * ave_temp;
       } else {
         dry_season = "f";
         dry_limit = 20 * (ave_temp + 7);
       }

       if (year_rain >= dry_limit) {
         if (low_temp >= 18) {
           if (min_rain >= 60) {
             climate = "Af";
           } else if (year_rain >= 2500 - min_rain * 25) {
             climate = "Am";
           } else {
             climate = "Aw";
           }
         } else if (low_temp >= -3) {
           switch (dry_season) {
              case "w":
                climate = "Cw";
                break;
              case "s":
                climate = "Cs";
                break;
              case "f":
                if (high_temp >= 22) {
                  climate = "Cfa";
                } else {
                  climate = "Cfb";
                }
                break;
           }
         } else {
           switch (dry_season) {
              case "w":
                climate = "Cw";
                break;
              case "s":
                climate = "Cs";
                break;
              case "f":
                climate = "Df";
                break;
         }
        }
       } else if (year_rain >= 0.5 * dry_limit) {
         climate = "BS";
       } else {
         climate = "BW";
       }

     }else if(high_temp >= 0) {
       climate = "ET";
     }else {
       climate = "EF";
     }

     System.out.println(climate);


  } catch(IOException e) {
    System.out.println(e);
  }


}
}
