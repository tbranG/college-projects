/// @author Túlio Brant
/// @version 1.0
/// @copyright Copyright (c) All rigths reserved. 2022

#include<iostream>
#include<stdlib.h>
#include<time.h>
#include "bet.h"
#include "ui.cpp"
#include "ai.cpp"

using namespace std;

/// @return returns a value between 1 and 13
int cardGenerator() { return ((rand() % 13) + 1); }

int main(){
    srand(time(NULL));
    bool run = true;
    while (run)
    {
        cout << "Iniciar partida?" << endl;
        cout << "0-no  1-yes" << endl;
        int in = -1;
        cin >> in;

        switch (in)
        {
            case 0:
                run = false;
                break;
            case 1: {
                Bet player, ai;
                cout << "Sacando as cartas" << endl;

                player.addToBet(cardGenerator());
                ai.addToBet(cardGenerator());

                printBoard(ai.getBets(), ai.getBetCount(), player.getBets(), player.getBetCount());

                //comandos
                int opt = -1, round = 0;   //0 - passar, 1 - comprar               
                bool game = true, playerFolded = false, aiFolded = false, turn = true;   //controla de quem é o turno


                while (game){
                    round++;
                    cout << "Round: " << round << endl;

                    //check game state
                    if(player.getTotal() > 21){
                        cout << "Player Estorou!" << endl;
                        game = false;
                        break;
                    }else if(ai.getTotal() > 21){
                        cout << "IA Estorou!" << endl;
                        game = false;
                        break;
                    }

                    if(turn){
                        if(!playerFolded){
                            cout << "0-passar  1-comprar" << endl;
                            cin >> opt;

                            cout << endl;
                            if(opt == 1){
                                player.addToBet(cardGenerator());
                                cout << "Player comprou uma carta" << endl;
                                printBoard(ai.getBets(), ai.getBetCount(), player.getBets(), player.getBetCount());

                            }else{
                                playerFolded = true;
                                cout << "Player passou." << endl;
                                printBoard(ai.getBets(), ai.getBetCount(), player.getBets(), player.getBetCount());
                            }

                            if(!aiFolded) turn = !turn;
                        }
                    }else{
                        if(!aiFolded){
                            cout << endl;
                            if(choice(playerFolded, player, ai)){
                                ai.addToBet(cardGenerator());
                                cout << "IA comprou uma carta" << endl;
                                printBoard(ai.getBets(), ai.getBetCount(), player.getBets(), player.getBetCount());
                            }else{
                                aiFolded = true;
                                cout << "IA passou." << endl;
                                printBoard(ai.getBets(), ai.getBetCount(), player.getBets(), player.getBetCount());
                            }

                            if(!playerFolded) turn = !turn;
                        }
                    }
                    if(playerFolded && aiFolded){
                        //endgame
                        if(player == ai){
                            cout << "Empate" << endl;
                        }else if(player > ai){
                            cout << "O Player venceu!" << endl;
                        }else{
                            cout << "A IA venceu!" << endl;
                        }

                        game = false;
                    }
                    //printBoard(ai.getBets(), ai.getBetCount(), player.getBets(), player.getBetCount());
                }

                break;
            }
                
        default:
            break;
        }
    }
}