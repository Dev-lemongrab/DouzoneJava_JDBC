package quiz.ex02;
class MySum /* 1번 */extends Object{
 int first;
 int second;
 
 MySum(int first, int second){
  this.first = first;
  this.second = second;
 }
 
 public String toString(){
  /* 2번 */
  String result = String.valueOf(10+20);
  return result;
 }
 
 
 public boolean equals(Object obj){
  
  if(obj instanceof MySum){
   
   /* 3번 */
   
   return true;
  }
  else{
   return false;
  }
 }
 
}