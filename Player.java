public class Player
{ 
  private String name;
  private double money;
  private int score;
  
  public Player(String n,double m,int s)
  {
    name = n;
    money = m;
		score = s;
  }

  public String getName()
  {
    return name;
  }

  public double getMoney()
  {
    return money;
  }

  public int getScore() 
  {
    return score;
  }

  public void  loseMoney(double amount)
  {
    money = money - amount;
  }
	
  public void gainMoney(double amount)
  {
    money = money + amount;
  }
  
	public int increaseScore()
  {
    score++;
    return score;
  }

	public double setGamble(double w)
	{
		return Math.round(money - w *100)/100.0;
	}

}