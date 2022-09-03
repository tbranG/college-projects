#include<stdio.h>
#include<stdlib.h>
#include<math.h>
#include<time.h>

double myMoney = 0.0;

//controladores de vitória
int winCond = 0;
char winChar = '0';

char user[25];
char comb[3];

void drawBoard(){
    printf("\nDinheiro: $%.2lf\n", myMoney);
    printf("---------------------------+\n");
    printf("| %c | %c | %c |\n", comb[0], comb[1], comb[2]);
    printf("---------------------------+\n");
}

void initializeGame(){
    printf("Iniciando partida...\ndigite o nome do usuario:\n");
    scanf("%s", user);
    printf("Ola %s! valor inicial de $1000\n", user);
    myMoney = 1000.0;
}

int isWinner(){
    return (comb[0] == comb[1] && comb[1] == comb[2]);
}

void roll(){

    char a = (char)((rand() % 10) + 48);
    char b = (char)((rand() % 10) + 48);
    char c = (char)((rand() % 10) + 48);
    
    //checa condição de vitória
    if(winCond==1){
        a = winChar;
        b = a;
        c = a;
        winCond = 0;
    }else{
        if(a == b || b == c || a == c){
            winCond = 1;
            if(a == b){
                winChar = c;
            }else if(b == c){
                winChar = a;
            }else{
                winChar = b;
            }
        }
    }

    comb[0] = a; comb[1] = b; comb[2] = c;
}

//cria apostas
double makeBet(){
    double bet;
    printf("Quantidade para apostar:\n");
    scanf("%lf", &bet);

    if(bet >= myMoney){
        bet = myMoney;
        printf("All in\n");
    }

    return bet;
}

//calcula o pote do vencedor
double calcPrize(char n, double bet){
    double prize = 0;
    switch (n)
    {
        case '0':
            prize = bet + 100;
            break;
        case '1':
            prize = bet + 250;
            break;
        case '2':
            prize = bet + 500;
            break;
        case '3':
            prize = bet + 750;
            break;
        case '4':
            prize = bet + 1000;
            break;
        case '5':
            prize = bet + 2500;
            break;
        case '6':
            prize = bet + 5000;
            break;
        case '7':
            prize = bet + 10000;
            break;
        case '8':
            prize = bet + 50000;
            break;
        case '9':
            prize = bet + 100000;
            break;
        default:
            break;
    }

    return prize;
}

void displayScores(){
    FILE *arq = fopen("placar.txt", "r");
    
    char tmpStr1[50], tmpStr2[50];
    while ((fscanf(arq, "%s", tmpStr1) != EOF) && (fscanf(arq, "%s", tmpStr2) != EOF))
    {
        printf("%s\t%s\n", tmpStr1, tmpStr2);
    }
    
    fclose(arq);
}

int main(){
    
    int run = 1;

    srand(time(NULL));
    initializeGame();

    while (run==1)
    {
        int in;
        printf("1-Fazer aposta   2-Placar   3-Sair\n:");
        scanf("%d", &in);

        switch (in)
        {
            case 1: {

                double aposta = makeBet();
                myMoney -= aposta;
                
                roll();
                int result = isWinner();
                if(result==1){
                    myMoney += calcPrize(comb[0], aposta);
                    drawBoard();
                    printf("Vencedor!\n");
                }else{
                    drawBoard();
                    printf("Perdeu...\n");
                }
                break;
            }
            case 2:
                displayScores();
                break;
            case 3: {
                run = 0;

                //salvando partida
                FILE *arq = fopen("placar.txt", "a");
                fprintf(arq, "%s\t%lf\n", user, myMoney);
                fclose(arq);
                break;
            }
            default:
                break;
        }

        //deadzone para a derrota
        if(myMoney <= 0.1){
            printf("Sem dinheiro, fim de jogo.\n");
            run = 0;
        }
    }
    
    return 0;
}