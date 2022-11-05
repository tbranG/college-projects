/// @author Túlio Brant
/// @version 1.0
/// @copyright Copyright (c) All rights reserved. 2022

#include<iostream>
#include<stdlib.h>
#include "bet.h"

using namespace std;

bool choice(bool playerFolded, Bet playerBet, Bet aiBet){
    bool resp = false;

    int totalValuesOfCards = 91;
    //check every card in hand
    for(int i = 0; i < aiBet.getBetCount(); i++){
        totalValuesOfCards -= aiBet.getBet(i);
    }

    //calculates the possible card in the next draw
    int possibleNextCard = totalValuesOfCards / (13 - aiBet.getBetCount());
    
    //if player folded, try get a bigger value than him
    if(playerFolded){
        if(aiBet > playerBet){
            resp = false;
        }else{
            //check if it can get higher
            if(aiBet.getTotal() + possibleNextCard <= 21){
                resp = true;
            }
        }
    }else{
        //check if it can get higher
        if(aiBet.getTotal() + possibleNextCard <= 21){
            resp = true;
        }
    }

    return resp;
}
