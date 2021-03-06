package service;

import model.User;

import java.io.*;

public class FileUtilityService implements CrudUtility {

    public void save(User user) {
        try {
            FileWriter fileWriter = new FileWriter(user.getAccountNumber() + ".txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("AccountNumber:" + user.getAccountNumber() + "\n");
            bufferedWriter.write("Name:" + user.getName() + "\n");
            bufferedWriter.write("Address:" + user.getAddress() + "\n");
            bufferedWriter.write("Balance:" + user.getBalance() + "\n");
            bufferedWriter.write("PIN:" + user.getPIN());

            bufferedWriter.close();
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean searchAccount(String accountNumber) {
        try {
            File directory = new File("/home/nabin/projects/bankingapplication/");
            String[] fList = directory.list();

            if (fList == null)
                return false;
            else {
                // Linear search in the array
                for (String filename : fList) {
                    if (filename.equalsIgnoreCase(accountNumber + ".txt".trim()))
                        return true;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public User readAccount(String accountNumber) {
        try {
            User user = new User();
            FileReader fileReader = new FileReader(accountNumber + ".txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String[] strings;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                strings = line.split(":");
                switch (strings[0]) {
                    case "AccountNumber":
                        user.setAccountNumber(strings[1]);
                        break;
                    case "Name":
                        user.setName(strings[1]);
                        break;
                    case "Address":
                        user.setAddress(strings[1]);
                        break;
                    case "Balance":
                        user.setBalance(Double.parseDouble(strings[1]));
                        break;
                    case "PIN":
                        user.setPIN(Integer.parseInt(strings[1]));
                        break;
                }
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
