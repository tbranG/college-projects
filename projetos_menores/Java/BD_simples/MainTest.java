package base_lista;

import java.util.Scanner;

public class MainTest {    
    
    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);
        DataManager bancoD = new DataManager();
        
        boolean running = true;
        
        while(running){
            int in = 0;
            String tempId;
            
            System.out.println
            ("Banco de dados de funcionarios:\nacessos: 1-adicionar funcionario "
            + " 2-remover funcionario  3-ver funcionarios  4-achar funcionario  5-editar funcionario  6-sair");
            
            while(in < 1 || in > 6){
                System.out.println(":");
                in = scan.nextInt();
            }
            scan.nextLine();
            
            switch(in){
                case 1:
                    Funcionario funcionario = bancoD.criarFuncionario();
                    bancoD.AddFuncionario(funcionario);                  
                    break;
                case 2:
                    System.out.println("digite a ID do funcionario");
                    tempId = scan.nextLine();
                    
                    bancoD.RemoveFuncionario(tempId);
                    break;
                case 3:
                    bancoD.PrintarFuncionarios();
                    break;
                case 4:
                    System.out.println("digite a ID do funcionario");
                    tempId = scan.nextLine();
                    
                    bancoD.FindFuncionario(tempId);
                    break;
                case 5:
                    System.out.println("digite a ID do funcionario");
                    tempId = scan.nextLine();
                    
                    bancoD.EditFuncionario(tempId);
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    break;
            }
        }
    }
}
