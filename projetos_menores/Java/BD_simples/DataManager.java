package base_lista;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataManager {
    public List<Funcionario> listaDeFuncionarios;
    public List<String> idList;
    
    private int idSufix = 0;
    
    public DataManager(){
        listaDeFuncionarios = new ArrayList<Funcionario>();
        idList = new ArrayList<String>();
    }
    
    public static Funcionario criarFuncionario(){
        Scanner tempScan = new Scanner(System.in);
        String tempNome,tempNum,tempCargo;
        
        System.out.println("digite o nome do funcionario: ");
        tempNome = tempScan.nextLine();
        System.out.println("digite o numero do funcionario: ");
        tempNum = tempScan.nextLine();
        System.out.println("digite o cargo do funcionario: ");
        tempCargo = tempScan.nextLine();
        Funcionario f = new Funcionario(tempNome, tempNum, tempCargo);
        
        return f;
    }
    
    public void AddFuncionario(Funcionario novoF){
        generateID(listaDeFuncionarios, novoF);
        listaDeFuncionarios.add(novoF);
    }

    public void RemoveFuncionario(String id){
        
        int refIndex = -1;
        for(int i = 0; i < listaDeFuncionarios.size(); i++){
            if(id.equals(listaDeFuncionarios.get(i).id)){
                refIndex = i;
                break;
            }
        }
        
        if(refIndex >= 0 && refIndex < listaDeFuncionarios.size()){
            listaDeFuncionarios.remove(refIndex);
            System.out.println("Funcionario removido com sucesso.");
        }
        else
            System.out.println("Impossivel remover funcionario.(index invalido)");
    }
    
    public void FindFuncionario(String id){
         
        for(int i = 0; i < listaDeFuncionarios.size(); i++){
            if(id.equals(listaDeFuncionarios.get(i).id)){
                System.out.println("\nFuncionario encontrado:");
                System.out.println
                ("Nome: " + listaDeFuncionarios.get(i).nome + "\n"
                + "Telefone: " + listaDeFuncionarios.get(i).telefone + "\n"
                + "Cargo: " + listaDeFuncionarios.get(i).cargo + "\n" 
                + "ID: " + listaDeFuncionarios.get(i).id + "\n"
                );
                return;
            }
        }
        
        System.out.println("funcionario nao encontrado.");
    }
    
    public void PrintarFuncionarios(){
        int listSize = listaDeFuncionarios.size();
        
        System.out.println("\t--Funcionarios da empresa--\n");
        for(int i = 0; i < listSize; i++){
            System.out.println("Funcionario " + (i + 1) + ":");
            System.out.println
            ("Nome: " + listaDeFuncionarios.get(i).nome + "\n"
            + "Telefone: " + listaDeFuncionarios.get(i).telefone + "\n"
            + "Cargo: " + listaDeFuncionarios.get(i).cargo + "\n" 
            + "ID: " + listaDeFuncionarios.get(i).id + "\n"
            );
        }
    }

    public void EditFuncionario(String id){
        Scanner tempScan = new Scanner(System.in);
        
        int index = -1;
        for(int i = 0; i < listaDeFuncionarios.size(); i++){
            if(id.equals(listaDeFuncionarios.get(i).id)){
                index = i;
                break;
            }
        }
    
        if(index < 0 || index >= listaDeFuncionarios.size()){
            System.out.println("Funcionario nao encontrado.");
            return;
        }
    
        System.out.println("Digite as novas informacoes: ");
        String tempNome = tempScan.nextLine();
        String tempNum = tempScan.nextLine();
        String tempCargo = tempScan.nextLine();
        
        Funcionario fRef = listaDeFuncionarios.get(index);
        fRef.nome = tempNome;
        fRef.telefone = tempNum;
        fRef.cargo = tempCargo;
    }
    
    private void generateID(List<Funcionario> funcList, Funcionario fObj){
        fObj.id = "0xf" + idSufix;
        idList.add(fObj.id);
        
        idSufix++;
    }
}
