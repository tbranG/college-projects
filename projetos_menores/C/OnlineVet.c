/*
LISTA AVALIATIVA 2
Desenvolvedor (es): Túlio Brant Silva Guerra
*/

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#define limit 10

// quantidade de donos = dQuant
// quantidade de animais = aQuant
// quantidade de veterianarios = vQuant
// quantidade de consultas = cQuant
int dQuant = 0, aQuant = 0, vQuant = 0, cQuant = 0;

int compararStrings(char a[], char b[]){
    // 0 são iguais
    // -1 são diferentes
    int result = -1;

    if(strcmp(a, b) != 0){
        result = -1;
        return result;
    }
    // se passar é porque tem tamanhos iguais

    int sLen = strlen(a);
    int match = 0;

    for(int i = 0; i<sLen; i++){
        if(a[i] == b[i]){
            match++;
        }
    }

    if(match==sLen){
        result = 0;
    }

    return result;
}

#pragma region registros

typedef struct Dono{
    char *nome;
    char *telefone;
} dono;

typedef struct Animal{
    char *nome;
    int idade;
    float peso;
    dono *resp;
} pet;

typedef struct Veterinario{
    char *nome;
    int CFMV;
} vet;

typedef struct Consulta{
    char *data;
    int horario;
    pet *animal;
    dono *resp;
    vet *veterinario;
} consulta;

#pragma endregion registros

#pragma region funci

void imprimirDono(dono *d){
    printf("\nInfo do responsavel:\n");
    printf("\tnome: %s\n", d->nome);
    printf("\ttel: %s", d->telefone);
}

void imprimirVeterinario(vet *v){
    printf("\nInfo do veterinario:\n");
    printf("\tnome: %s\n", v->nome);
    printf("\tCFMV: %d", v->CFMV);
}

void imprimirAnimal(pet *p){
    printf("\nInfo do animal:\n");
    printf("\tnome: %s\n",p->nome);
    printf("\tidade: %d\n",p->idade);
    printf("\tpeso: %.1f\n",p->peso);
    printf("\tdono: %s",p->resp->nome);
}

#pragma endregion funci

#pragma region funcb

int buscarDono(char nome[], dono lista[], int cadQuant){
    for(int i = 0; i < cadQuant; i++){     
        int nD = compararStrings(lista[i].nome, nome);
        if(nD == 0){
            printf("\nDono encontrado!\n");
            
            char in;
            printf("Imprimir info?\n");
            printf("y/n: ");
            scanf(" %c", &in);
            
            if(in == 'y' || in == 'Y'){
                imprimirDono(&lista[i]);
            } 
            printf("\n");
            return i;
        }
            
    }

    printf("\nNao encontrado.\n");
    return -1;
}

int buscarAnimal(char nome[], pet lista[], int cadQuant){
    for(int i = 0; i < cadQuant; i++){
        int nA = compararStrings(lista[i].nome, nome);
       
        if(nA == 0){ 
            printf("\nAnimal encontrado!\n");
            
            char in;
            printf("Imprimir info?\n");
            printf("y/n: ");
            scanf(" %c", &in);
            
            if(in == 'y' || in == 'Y'){
                imprimirAnimal(&lista[i]);
            }
            printf("\n");
            return i;
        }
    }

    printf("\nNao encontrado.\n");
    return -1;
}

int buscarVeterinario(char nome[], vet lista[], int cadQuant){
    for(int i = 0; i < cadQuant; i++){
        int test = compararStrings(lista[i].nome, nome);
        if(test == 0){
            printf("\nVeterinario Encontrado!\n");
            
            char in;
            printf("Imprimir info?\n");
            printf("y/n: ");
            scanf(" %c", &in);
            
            if(in == 'y' || in == 'Y'){
                imprimirVeterinario(&lista[i]);
            }     
          
            printf("\n");
            return i;
        }
    }

    printf("\nNao encontrado.\n");
    return -1;
}

#pragma endregion funcb

#pragma region funcc

void cadastrarDono(dono lista[], int *cadQuant){
    if(*cadQuant >= limit){
        printf("Lista cheia.\n");
        printf("Cadastro cancelado...\n\n");
        return;
    }

    dono novo_dono;
    novo_dono.nome = (char*)malloc(20 * sizeof(char));
    novo_dono.telefone = (char*)malloc(10*sizeof(char));

    printf("\t--Cadastro de Dono--\n\n");
    printf("nome do dono: ");
    
    setbuf(stdin, NULL);
    gets(novo_dono.nome);

    int nlen = strlen(novo_dono.nome);
    novo_dono.nome = (char*)realloc( novo_dono.nome, nlen * sizeof(char));

    printf("telefone do dono: ");
    setbuf(stdin, NULL);
    gets(novo_dono.telefone);

    int tlen = strlen(novo_dono.telefone);
    novo_dono.telefone = (char*)realloc(novo_dono.telefone, tlen * sizeof(char));

    lista[*cadQuant] = novo_dono;
    (*cadQuant)++;

    printf("\n\t--Cadastro completo!--\n");
}

void cadastrarVet(vet lista[], int *cadQuant){
    if(*cadQuant >= limit){
        printf("Lista cheia.\n");
        printf("Cadastro cancelado...\n\n");
        return;
    }

    vet novo_vet;
    novo_vet.nome = (char*)malloc(20 * sizeof(char));

    printf("\t--Cadastro de Veterinario--\n\n");
    printf("nome do veterinario: ");

    setbuf(stdin, NULL);
    gets(novo_vet.nome);

    printf("CFMV: ");
    fflush(stdin);
    scanf("%d", &novo_vet.CFMV);

    lista[*cadQuant] = novo_vet;
    (*cadQuant)++;

    printf("\n\t--Cadastro Completo!--\n");
}

void cadastrarAnimal(pet pets[], int *petQuant, dono donos[], int donQuant){
    if(*petQuant >= limit){
        printf("Lista cheia.\n");
        printf("Cadastro cancelado...\n\n");
        return;
    }
    
    printf("\t--Cadastro de Animal--\n\n");

    pet novo_pet;
    novo_pet.nome = (char*)malloc(20*sizeof(char));
    char *nomeDono = (char*)malloc(20*sizeof(char));

    int a = -1;
    while(a == -1){ 
        printf("Nome do dono: ");
        setbuf(stdin, NULL);
        gets(nomeDono);

        a = buscarDono(nomeDono, donos, donQuant);

        if(a == -1){
            char in;
        
            printf("\ndono nao encontrado!\n");
            printf("tentar novamente?\n");
            printf("y/n: ");
            scanf(" %c", &in);

            if(in == 'y' ||in == 'Y'){
                continue;
            }else if(in == 'n' || in == 'N'){
                printf("Cadastro cancelado...");
                free(nomeDono);
                return;
            }else{
                printf("entrada invalida.\n");
                printf("\tRetornando.");
                free(nomeDono);
                return;
            }
        }else{
            break;
        }
    }

    novo_pet.resp = &donos[a];
    free(nomeDono);

    printf("Digite o nome do pet: ");
    setbuf(stdin,NULL);
    gets(novo_pet.nome);

    int len = strlen(novo_pet.nome);
    novo_pet.nome = (char*)realloc(novo_pet.nome, len * sizeof(char));

    fflush(stdin);
    printf("Digite a idade do pet: ");
    scanf("%d", &novo_pet.idade);
    printf("Digite o peso do animal: ");
    scanf("%f", &novo_pet.peso);

    pets[*petQuant] = novo_pet;
    (*petQuant)++;

    printf("\n\t--Cadastro Concluido--\n");
}

#pragma endregion funcc

int horarioDisponivel(consulta consultas[], int qtdCon, char data[], int hora){
    if(qtdCon==0){
        if(consultas[0].horario == hora)
            return 0;
        else
            return 1;
    }

    if(consultas[qtdCon - 1].horario == hora)
        return horarioDisponivel(consultas, qtdCon - 1, data, hora);
    else
        return 1;
    
}

void agendarConsulta(consulta consultas[], int *qtdCon, pet animais[], int qtdAnim, vet veterinarios[], int qtdVet){
    if(qtdAnim >= limit){
        printf("\nLista cheia.\n");
        printf("impossivel agendar consulta\n");
        return;
    }

    printf("\n\t----Agendar Consulta----\n");

    consulta nova_consulta;
    
    char *buscaNome = (char*)malloc(20*sizeof(char));
    int x = -1;

    while (x == -1)
    {
        printf("Digite o nome do veterinario: ");
        setbuf(stdin, NULL);
        gets(buscaNome);

        x = buscarVeterinario(buscaNome, veterinarios, vQuant);

        if(x == -1){

            char in;
            printf("\nVeterianrio nao encontrado.\n");
            printf("tentar novamente?\n");
            printf("y/n: ");
            scanf(" %c", &in);

            if(in == 'y' ||in == 'Y'){
                continue;
            }else if(in == 'n' || in == 'N'){
                printf("Busca cancelada...");
                free(buscaNome);
                return;
            }else{
                printf("entrada invalida.\n");
                printf("\tRetornando.");
                free(buscaNome);
                return;
            }
        }else{
            break;
        }
    }
    nova_consulta.veterinario = &veterinarios[x];

    x = -1;
    while (x == -1)
    {
        printf("digite o nome do animal: ");
        setbuf(stdin, NULL);
        gets(buscaNome);

        x = buscarAnimal(buscaNome, animais, aQuant);
        if(x == -1){

            char in;
            printf("\nAnimal nao encontrado.\n");
            printf("tentar novamente?\n");
            printf("y/n: ");
            scanf(" %c", &in);

            if(in == 'y' ||in == 'Y'){
                continue;
            }else if(in == 'n' || in == 'N'){
                printf("Busca cancelada...");
                free(buscaNome);
                return;
            }else{
                printf("entrada invalida.\n");
                printf("\tRetornando.");
                free(buscaNome);
                return;
            }
        }else{
            break;
        }
    }
    nova_consulta.animal = &animais[x];
    nova_consulta.resp = nova_consulta.animal->resp;

    nova_consulta.data = (char*)malloc(8*sizeof(char));

    printf("Digite a data: ");
    setbuf(stdin, NULL);
    gets(nova_consulta.data);

    printf("Digite o horario: ");
    fflush(stdin);
    scanf("%d", &nova_consulta.horario);

    int a = 0;
    while(a==0){
        a = horarioDisponivel(consultas, cQuant, nova_consulta.data, nova_consulta.horario);

        if(a==0){
            char in;
            printf("Horario nao disponivel :(\n");
            printf("Selecionar outro horario?\n");
            printf("y/n:");
            scanf(" %c", &in);

            if(in == 'y' ||in == 'Y'){
                printf("Digite um novo horario: ");
                scanf("%d", &nova_consulta.horario);

                continue;
            }else if(in == 'n' || in == 'N'){
                printf("Consulta cancelada...");
                return;
            }else{
                printf("entrada invalida.\n");
                printf("\tRetornando.");
                return;
            }
        }else{
            break;
        }
    }

    consultas[*qtdCon] = nova_consulta;
    (*qtdCon)++;

    printf("\nConsulta agendada com sucesso!\n\n");
    free(buscaNome);
}

void visualizarAgenda(consulta consultas[], int qtdCon){
    
    char *data = (char*)malloc(8*sizeof(char));
    printf("\n----Agenda----");
    printf("\nDigite uma data (dd/mm/aaaa): ");
    setbuf(stdin, NULL);
    gets(data);

    for(int i = 0; i < qtdCon; i++){
        int comp = compararStrings(data, consultas[i].data);

        if(comp==0){
            printf("\nConsulta %d:\n",i+1);
            printf("--------------------+\n");
            printf("Data: %s\n",data);
            printf("Horario: %d\n",consultas[i].horario);
            printf("Veterinario: %s\n",consultas[i].veterinario->nome);
            printf("Animal: %s\n",consultas[i].animal->nome);
            printf("Dono: %s\n",consultas[i].resp->nome);
            printf("--------------------+\n");
        }
    
        if(i==(qtdCon - 1)){
            free(data);
            return;
        }
    }

    printf("Agenda vazia.\n");
    free(data);
}

void main(){
    dono donos[limit];
    pet animais[limit];
    vet veterinarios[limit];
    consulta consultas[limit];   
    
    // variaveis para busca
    char *tempString1 = (char*)malloc(30*sizeof(char));
    char *tempString2 = (char*)malloc(30*sizeof(char));

    int running = 1;
    while(running){
        int in = 0;
        
        printf("\t------------Online Vet 1.0------------\n");
        printf("\nOpcoes do usuario:\n\n--Cadastrar--:\n1-Cad. Pet   2-Cad. Dono   3-Cad. Veterinario\n\n");
        printf("--Buscar--:\n4-Busc. Pet   5-Busc. Dono   6-Busc.Veterinario\n\n--Agendar--:\n7-Ag. nova consulta\n\n");
        printf("--Visualizar--:\n8-Vis. Agenda\n\n--Finalizar--:\n9-sair");
       
        while (in < 1 || in > 9)
        {
            printf("\nEntrada: ");
            scanf("%d", &in);
        }
        
        switch (in)
        {
        case 1:
            cadastrarAnimal(animais, &aQuant, donos, dQuant);
            break;
        case 2:
            cadastrarDono(donos, &dQuant);
            break;
        case 3:
            cadastrarVet(veterinarios, &vQuant);
            break;
        case 4:
            printf("\n----Sistema de Busca----\n\n");
            printf("digite o nome do pet: ");
            setbuf(stdin, NULL);
            gets(tempString1);

            buscarAnimal(tempString1, animais, aQuant);  
            system("pause");
            break;    
        case 5:
            printf("\n----Sistema de Busca----\n\n");
            printf("digite o nome do dono: ");
            setbuf(stdin, NULL);
            gets(tempString1);

            buscarDono(tempString1, donos, dQuant);
            system("pause");
            break;
        case 6:
            printf("\n----Sistema de Busca----\n\n");
            printf("digite o nome do veterinario: ");
            setbuf(stdin, NULL);
            gets(tempString1);

            buscarVeterinario(tempString1, veterinarios, vQuant);
            system("pause");
            break;
        case 7:
            agendarConsulta(consultas, &cQuant, animais, aQuant, veterinarios, vQuant);
            break;
        case 8:
            visualizarAgenda(consultas, cQuant);
            system("pause");
            break;
        case 9:
            printf("\nEncerrando...");
            return;
        default:
            break;
        }
    }
}