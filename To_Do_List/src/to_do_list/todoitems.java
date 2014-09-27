package to_do_list;

class todoitems//the basis of my list, to hold single task items
{
	String todoname;// it obiously needs to have a name, otherwise ppl don't know what they should do
	Boolean done;//was the task done? and checkmark can be applied?"
	Boolean selected;// it is one of the item selected for email

	public todoitems(String todoname) 
	{
		// TODO Auto-generated constructor stub
		//defualt values that makes sense to me
		this.todoname = todoname;
		this.done = false;
		this.selected = false;
	}
}