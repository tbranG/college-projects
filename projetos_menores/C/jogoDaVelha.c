/*
    autor: Túlio Brant Silva Guerra
    Todos os direitos reservados.
*/
#include<stdio.h>
#include<stdlib.h>
#include<stdbool.h>
#include<time.h>
#define rowSize 3
#define rowQtd 3

char playerChar;
char opponentChar;
int emptySpots;

void printBoard(char board[3][3]){
    
    printf("Tabuleiro atual:\n\n");
    for(int i = 0; i < rowQtd; i++){
        printf(" %c | %c | %c\n", board[i][0], board[i][1], board[i][2]);
        if(i!=2)
            printf("-----------\n");
    }
}

bool checkBoard(char board[3][3], int row, int columm){
    
    bool check = true;
    if(board[row][columm] == 'x' || board[row][columm] == 'o')
        check = false;

    return check;
}

bool victoryTrigger(char board[3][3]){
    
    bool endGame = false;
    // checagem horizontal
    for(int i = 0; i < rowSize; i++){
        if(board[i][0] == board[i][1] && board[i][1] == board[i][2]){
            if(board[i][0] == 'x' || board[i][0] == 'o'){
                endGame = true;
                return endGame;
            }
        }
    }
    // checagem vertical
    for(int i = 0; i < rowSize; i++){
        if(board[0][i] == board[1][i] && board[1][i] == board[2][i]){
            if(board[0][i] == 'x' || board[0][i] == 'o'){
                endGame = true;
                return endGame;
            }
        }
    }

    // checagem diagonal
    if(board[0][0] == board[1][1] && board[1][1] == board[2][2]){
         if(board[0][0] == 'x' || board[0][0] == 'o'){
                endGame = true;
                return endGame;
            }
    }
    else if(board[0][2] == board[1][1] && board[1][1] == board[2][0]){
        if(board[0][2] == 'x' || board[0][2] == 'o'){
            endGame = true;
            return endGame;
        }
    }
    return endGame;
}

void addToBoard(char board[3][3], int* spots, int row, int columm, char letter){

    bool canPlace = checkBoard(board, row, columm);
    if(canPlace){
        board[row][columm] = letter;
        (*spots)--;
    }else
        printf("\nLocal ja preenchido.\n\n");
       
}

void boardPlaceSelector(char board[3][3], int* row, int* columm){
    int input;
    int coords[2];

    bool canSelect = false;

    do{
        printf("Proximo.\n-> ");
        scanf("%d", &input);
        fflush(stdin);
    }while(input < 1 || input > 9);
  
    switch (input)
    {
        case 1:
            coords[0] = 0;
            coords[1] = 0;
            break;
        case 2:
            coords[0] = 0;
            coords[1] = 1;
            break;
        case 3:
            coords[0] = 0;
            coords[1] = 2;
            break;
        case 4:
            coords[0] = 1;
            coords[1] = 0;
            break;
        case 5:
            coords[0] = 1;
            coords[1] = 1;
            break;
        case 6:
            coords[0] = 1;
            coords[1] = 2;
            break;
        case 7:
            coords[0] = 2;
            coords[1] = 0;
            break;
        case 8:
            coords[0] = 2;
            coords[1] = 1;
            break;
        case 9:
            coords[0] = 2;
            coords[1] = 2;
            break;
        default:
            break;
    }

    canSelect = checkBoard(board, coords[0], coords[1]);
    if(!canSelect){
        boardPlaceSelector(board, row, columm);
        return;
    }

    *row = coords[0];
    *columm = coords[1];
}

void iaMove(char board[3][3]){
    
    if(emptySpots==0) return;
    
    int row, columm;
    row = rand() % 3;
    columm = rand() % 3;

    bool canPLace = checkBoard(board, row, columm);
    if(!canPLace){
        iaMove(board);
        return;
    }else{
        addToBoard(board, &emptySpots, row, columm, opponentChar);
    }
}

int main(){
    
    srand(time(NULL));
    
    //matriz do tabuleiro
    char board[3][3] = {{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
    //tabuleiro inicia com 9 espaços vazios
    emptySpots = 9;     
    bool run = true;

    printf("Jogo da velha\nX ou O?\n-> ");
    scanf(" %c", &playerChar);

    //define o caractere do oponente
    opponentChar = playerChar == 'x' ? 'o' : 'x';

    while (run)
    {
        //cada iteração do loop é um round
        //em cada loop temos:
        //print do tabuleiro, rodada do player, rodada da IA, checagem de vitória
        printBoard(board);
        int row, columm;
        
        boardPlaceSelector(board, &row, &columm);
        addToBoard(board, &emptySpots, row, columm, playerChar);
        run = !victoryTrigger(board);

        if(!run){
            printBoard(board);
            printf("\nO jogador venceu!\n\n");
            system("pause");
            return 0;
        }  

        // turno da ia
        iaMove(board);
        run = !victoryTrigger(board);

        // checagem da vitória da IA
        if(!run){
            printBoard(board);
            printf("\nA IA venceu!\n\n");
            system("pause");
            return 0;
        }             
    
        // checa se ainda há espaços vazios no tabuleiro
        // caso não haja é um empate
        if(emptySpots <= 0){
            run = false;
            
            printBoard(board);
            printf("\nEmpate!\n\n");
            system("pause");
            return 0;
        }
    }
    
    return 0;
}