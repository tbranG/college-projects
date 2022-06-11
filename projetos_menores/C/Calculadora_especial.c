#include<stdio.h>

void main(){
    int in = 0;     // controla todo o funcionamento do programa
    int iterac = 0;     // quantas vezes o programa rodou sem sucesso

    while(in != 5){     // 5 é o valor que determina o encerramento do programa

        //1-Log de 10 na base 10
        float log;
        float r = 10;     // valor para comparar
        int elev = 1;      // valor o qual 10 deve ser elevado para dar x

        //2-MDC
        int n1, n2 = 0;
        int r1, r2 = 0;
        int mdc = 0;

        //3-Tipo de Caractere
        char c;

        //4-Tabuada
        int num = 0;

        // só irá printar se estiver iniciando o programa, 
        // ou depois de uma execução bem sucedida
        if(iterac==0){     
            printf("\nCalculadora Especial\n");
            printf("Opcoes: 1-log10  2-MDC  3-TipoCaractere  4-Tabuada  5-sair\n");
        }      
        
        printf("Entrada (1 - 5): ");   
        scanf("%d", &in);    

        switch(in){
            //Log na base 10
            case 1:   
                printf("\nApenas potencias de 10.\n");            
                printf("valor do logaritmo (log 10 x): x = ");
                scanf("%f", &log);

                // r inicia com valor 10
                // resultados corretos apenas para potências de 10
                if(log<=1){
                    while(r>log){
                        r /= 10;
                        elev--;
                    }
                }
                else{
                    while(r<log){
                        r *= 10;
                        elev++;
                    }
                }

                if(r<1){
                    printf("log%f = %d\n\n", log, elev);
                }
                else{
                    printf("log%.0f = %d\n\n", log, elev);
                }
                              
                in = 0;
                iterac = 0;
                
                break;
            
            //MDC
            case 2:
                printf("\ndigite dois numeros inteiros: ");
                scanf("%d %d", &n1,&n2);
                
                for(int i=n1; i >=1; i--){
                    if(mdc != 0)
                        break;
                    
                    if(n1 % i == 0){                 
                        r1= i;
                        
                        for(int j=n2; j>=1; j--){
                            
                            if(n2 % j == 0)
                                r2 = j;
                            
                            if(r1 == r2){
                                mdc = r1;
                                break;
                            }
                        }
                    }
                }

                printf("MDC de %d e %d = %d\n\n", n1,n2,mdc);
                
                in = 0;
                iterac = 0;

                break;
            
            //Checagem de input
            case 3:
                printf("\ninsira um caractere: ");
                scanf(" %c", &c);

                if(c >= 65 && c <= 90)
                    printf("O caractere '%c' eh uma letra\n\n", c);
                else if(c >= 97 && c <= 122)
                    printf("caractere '%c' eh uma letra\n\n", c);
                else if(c >= 48 && c <= 57)
                    printf("O caractere '%c' eh um numero\n\n", c);
                else if(c >= 59 && c <= 62)
                    printf("O caractere '%c' eh um operador\n\n", c);               
                else
                    switch(c){
                        case 33: case 37: case 38: case 124: case 43: case 45:
                            printf("O caractere '%c' eh um operador\n\n", c);
                            break;
                        default:
                            printf("%c: caractere especial\n\n", c);
                    }

                in = 0;
                iterac = 0;
                
                break;
            
            //Tabuada do número escolhido
            case 4:
                printf("\ndigite um numero inteiro: ");
                scanf("%d", &num);

                printf("\n-----------------------------------\n");

                for(int i=1; i<=10; i++){
                    printf("| %d x %d = %d |\n", num, i, num*i);
                }
                
                printf("-----------------------------------\n\n");
                
                in = 0;
                iterac = 0;
                
                break;
            
            // encerramento do programa
            case 5:
                printf("\nfinalizando programa...\n");
                break; 


            // caso o valor da entrada não estiver dentro das opções
            default:                
                printf("Entrada invalida, tente novamente\n");   
                
                in = 0;
                iterac++;

                /* 
                corrige o loop infinito causado quando a entrada 
                não é um número
                */
                fflush(stdin);      

                break;                
        }

    }
}