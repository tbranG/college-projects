#include<iostream>

class Exception{
    private:
        std::string msg;
    public:
        Exception(std::string msg){
            this->msg = msg;
        }
        std::string getMessage(){ return msg; }
};

/// @brief Replace the numeric values of 1, 11, 12 and 13 with their cards counterparts A, J, Q, K
/// @param value the card value
/// @return the char representation of the card
char valueFixer(int value){
    switch(value){
        case 1: 
            return 'A';
        case 11:
            return 'J';
        case 12:
            return 'Q';
        case 13:
            return 'K';
        default:
            throw new Exception("Valor invalido");
    }
}

/// @brief creates the game board, featuring the ai and player hand
/// @param value_1 ai cards
/// @param bets1 number of ai cards
/// @param value_2 player cards
/// @param bets2 number of player cards
void printBoard(int value_1[], int bets1, int value_2[], int bets2){
    std::cout << "===============================================" << std::endl;
    //machine hand
    std::cout << "IA" << std::endl;
    int p = 0, a = 0;
    for(int i = 0; i < bets1; i++){
        if(value_1[i] >= 2 && value_1[i] <= 10){
            std::cout << value_1[i] << " ";
        }else{
            char fixed = ' ';
            try{
               fixed = valueFixer(value_1[i]);
            }catch(Exception err){
                std::cout << err.getMessage();
            }
            std::cout << fixed << " ";
        }
        a += value_1[i];
    }
    std::cout << std::endl;
    std::cout << "Total: " << a << std::endl << std::endl;
    
    //player hand
    std::cout << "Player" << std::endl;
    for(int i = 0 ; i < bets2; i++){
        if(value_2[i] >= 2 && value_2[i] <= 10){
            std::cout << value_2[i] << " ";
        }else{
            char fixed = ' ';
            try{
               fixed = valueFixer(value_2[i]);
            }catch(Exception err){
                std::cout << err.getMessage();
            }
            std::cout << fixed << " ";
        }
        p += value_2[i];
    }
    std::cout << std::endl;
    std::cout << "Total: " << p << std::endl << std::endl;
    std::cout << "===============================================" << std::endl;
    //create a console space
    std::cout << std::endl;
}
