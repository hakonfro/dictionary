import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class Dictionary{
  BinNode rot;
  private int maxDybde = 0;
  private int antallNoder = 0;
  ArrayList<String> likeOrd;

  public Dictionary(String fil){
    lesFraFil(fil);
    visMeny();
  }

  public void visMeny(){
    Scanner tastatur = new Scanner(System.in);
    System.out.println("Please search after a word:");
    String input = tastatur.nextLine().toLowerCase();
    while(!input.equals("q")){
      BinNode resultat = finn(input, rot);
      if(resultat == null){
        System.out.printf("did not find %s \n", input);
        likeOrd = similar(input);
        for(String ord: likeOrd){
          BinNode print = finn(ord, rot);
          if (print != null){
            System.out.printf("Did you ment %s ? \n", print.data);
          }
        }

      } else{
        System.out.println("Found word: " + resultat.data);
      }
      System.out.println("\nPlease search after a word:");
      input = tastatur.nextLine().toLowerCase();
    }
    //Avslutter programmet
    this.firstNlast();
    finnDybde(0,rot);
    System.out.printf("Max depth is %s \n", maxDybde);
    antallNoder(rot);
    Double average = (double)antallNodeDybder(rot)/antallNoder;
    System.out.printf("Avarage depth: %.2f \n", average);
    for (int i = 0; i< maxDybde + 1; i++){
      int antall = rot.noderPerDybde(i);
      System.out.println("It contains: " + antall + " nodes on depth " + i);
    }
  }

  private class BinNode{
    BinNode left;
    BinNode right;
    String data;
    int egendybde;
    public BinNode(String data){
        this.data = data;
    }

    public int noderPerDybde(int lvl){
      int teller = 0;
      if(this.egendybde == lvl){
        teller++;
      } else{
        if(this.left != null){
          teller += this.left.noderPerDybde(lvl);
        }
        if(this.right != null){
          teller += this.right.noderPerDybde(lvl);
        }
      }
      return teller;
    }
  }

  public void lesFraFil(String fil){
    try{
      Scanner in = new Scanner(new File(fil));

      while(in.hasNextLine()){
        String ord = in.nextLine();
        settInn(ord, rot);
      }
    }
    catch(Exception e){
      System.out.println(e);
    }
  }

  public void settInn(String ord, BinNode n){
    if(n == null){
      rot = new BinNode(ord);
    } else if(ord.compareTo(n.data) < 0){
      if(n.left == null){
        n.left = new BinNode(ord);
      } else{
        settInn(ord, n.left);
      }
    }

    else if(ord.compareTo(n.data) > 0){
      if(n.right == null){
        n.right = new BinNode(ord);
      } else{
        settInn(ord, n.right);
      }
    }
  }

  public BinNode finn(String ord, BinNode n){
    if(n == null){
      return null;
    } else if(ord.compareTo(n.data) < 0){
      return finn(ord, n.left);
    } else if(ord.compareTo(n.data) > 0){
      return finn(ord, n.right);
    } else{
      return n;
    }
  }

  public ArrayList<String> similar(String input){
    ArrayList<String> stringArray = new ArrayList<String>();

    char[] inputChar;
    //First kind of similar input
    for(int i = 0; i < (input.length() - 1); i++){
      inputChar = input.toCharArray();
      char tmp = inputChar[i];
      inputChar[i] = inputChar[i + 1];
      inputChar[i +1] = tmp;
      stringArray.add(new String(inputChar));
    }
    //Second kind of similar input
    for(int j = 0; j < input.length(); j++){
      inputChar = input.toCharArray();
      for(char i = 'a'; i <= 'z'; i++){
        inputChar[j] = i;
        stringArray.add(new String(inputChar));
      }
    }

    //Third kind of similar input
    StringBuffer sb = new StringBuffer();
    for(int i = 0; i < input.length() + 1; i++){
      for(char c = 'a'; c <= 'z'; c++){
        sb.append(input);
        sb.insert(i, c);
        stringArray.add(sb.toString());
        sb.setLength(0);
      }
    }
    //Fourth kind of similar input
    String tmp;
    String tmp2;
    String tmp3;
    for(int i = 0; i<input.length(); i++){
      tmp = input.substring(0, i);
      tmp2 = input.substring(i+1, input.length());
      tmp3 = tmp + tmp2;
      stringArray.add(tmp3);

    }
    return stringArray;
  }

  public void firstNlast(){
    BinNode tmp = rot;
    while(tmp.left != null){
      tmp = tmp.left;
    } System.out.println("The first word is: " + tmp.data);
    tmp = rot;
    while(tmp.right != null){
      tmp = tmp.right;
    } System.out.println("The last word is: " + tmp.data);
  }

  public void finnDybde(int i, BinNode n){
    n.egendybde = i;
    if(maxDybde < i){
      maxDybde++;
    }

    if(n.left != null){
      finnDybde(i+1, n.left);
    }
    if(n.right != null){
      finnDybde(i+1, n.right);
    }
  }

  public void antallNoder(BinNode n){
    antallNoder++;
    if(n.left != null){
      antallNoder(n.left);
    }
    if(n.right != null){
      antallNoder(n.right);
    }
  }

  public int antallNodeDybder(BinNode n){
    int teller = 0;
    teller += n.egendybde;
    if(n.left != null){
      teller += antallNodeDybder(n.left);
    }
    if(n.right != null){
      teller += antallNodeDybder(n.right);
    }
    return teller;
  }
}
