using System;

namespace App{
    public class MeuPrograma {

        //set the possible characters to the generator
        private static string letters = "abcdefghijklmnopqrstuvwxyz";
        private static string numbers = "0123456789";
        private static string special = "@-/_$#";

        //settings
        private static int passwordLength = 8;
        private static int letLimit = 0;    //0 - disabled
        private static int numLimit = 2;    //0 - disabled
        private static int speLimit = 1;    //0 - disabled

        public static void Main(string[] args){
            try{
                string password = GeneratePassword();
                System.Console.WriteLine("Your password is: " + password);
            }catch(Exception err){
                System.Console.WriteLine(err.Message);
            }
        }

        public static string GeneratePassword(){
            if(letLimit != 0 && numLimit != 0 && speLimit != 0){
                if((letLimit + numLimit + speLimit) < passwordLength){
                    throw new Exception("Invalid configuration.");
                }
            }
            
            string p = "";
            Random rand = new Random();
            //counters for each type of char
            int l = 0, n = 0, s = 0;

            for(int i = 0; i < passwordLength; i++){
                //creates a random int
                int type = rand.Next() % 3;
                //check if there is a char limit
                if(l >= letLimit){
                    if(type == 0){
                        if(n >= numLimit){
                            type = 2;
                        }else if(s >= speLimit){
                            type = 1;
                        }else{
                            type = (rand.Next() % 2) + 1;
                        }
                    }
                }
                if(n >= numLimit){
                    if(type == 1){
                        if(l >= letLimit){
                            type = 2;
                        }else if(s >= speLimit){
                            type = 0;
                        }else{
                            type = (rand.Next() % 2) * 2;
                        }
                    }
                }
                if(s >= speLimit){
                    if(type == 2){
                        if(l >= letLimit){
                            type = 1;
                        }else if(n >= numLimit){
                            type = 0;
                        }else{
                            type = rand.Next() % 2;
                        }
                    }
                }

                //select random char from a type
                switch(type){
                    case 0: {
                        int ind = rand.Next() % letters.Length;
                        p += letters[ind];
                        l++;
                        break;
                    }
                    case 1: {
                        int ind = rand.Next() % numbers.Length;
                        p += numbers[ind];
                        n++;
                        break;
                    }
                    case 2: {
                        int ind = rand.Next() % special.Length;
                        p += special[ind];
                        s++;
                        break;
                    }
                    default: 
                        throw new Exception("Not implemented");
                }
            }
            return p;
        }
    }
}