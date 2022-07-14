import java.io.*;
import java.util.*;

// Tree node



public class OrgHierarchy implements OrgHierarchyInterface {

	//root node
	Node owner;
	AVLtree tree = new AVLtree();

	public boolean isEmpty() {
		//your implementation
		if (owner == null) {
			return true;
		} else {
			return false;
		}
	}

	public int size() {
		//your implementation
		return tree.size(tree.root);
	}

	public int level(int id) throws IllegalIDException {
		//your implementation
		Node node = tree.search(tree.root, id);
		if (node == null) {
			throw new IllegalIDException("Invalid ID");
		} else {
			int count = 1;
			while (node.boss != null) {
				count += 1;
				node = node.boss;
			}
			return count;
		}
	}

	public void hireOwner(int id) throws NotEmptyException {
		//your implementation
		if (owner != null) {
			throw new NotEmptyException("Not empty");
		} else {
			owner = tree.root = tree.insert(tree.root, id);
		}
	}

	public void hireEmployee(int id, int bossid) throws IllegalIDException {
		//your implementation
		Node node = tree.search(tree.root, bossid);
		if (node == null) {
			throw new IllegalIDException("Invalid bossId");
		} else {
			tree.insertNode(tree.root, id);
			Node employee = tree.search(tree.root, id);
			if (node.firstChild == null) {
				node.firstChild = employee;
				employee.boss = node;
			} else {
				employee.boss = node;
				node = node.firstChild;
				while (node.nextSibling != null) {
					node = node.nextSibling;
				}
				node.nextSibling = employee;
				employee.prevSibling = node;
			}

		}

	}

	public void fireEmployee(int id) throws IllegalIDException {
		//your implementation
		Node employee = tree.search(tree.root, id);
		if (employee == null) {
			throw new IllegalIDException("Invalid ID");
		} else if (employee == owner) {
			throw new IllegalIDException("Cannot fire Owner");
		} else {
			if (employee.boss.firstChild == employee) {
				if(employee.nextSibling!=null){
					if(employee.right==null || employee.left==null){
						Node newFirstChild = employee.nextSibling;
						employee.boss.firstChild = newFirstChild;
						employee.boss = null;
						newFirstChild.prevSibling = null;
						employee.nextSibling = null;
					}
				}
				else{
					employee.boss.firstChild=null;
					employee.boss=null;
				}
			} else {
				employee.boss = null;
				employee.prevSibling.nextSibling = employee.nextSibling;
				if(employee.nextSibling!=null){
					employee.nextSibling.prevSibling = employee.prevSibling;
				}

				employee.nextSibling = null;
				employee.prevSibling = null;
			}
			tree.deleteNode(tree.root, id);
		}
	}

	public void fireEmployee(int id, int manageid) throws IllegalIDException {
		//your implementation
		Node employee = tree.search(tree.root, id);
		Node newManager = tree.search(tree.root, manageid);
		if (employee == null || newManager == null) {
			throw new IllegalIDException("Either ID or manageID is invalid");
		} else if (employee == owner) {
			throw new IllegalIDException("Cannot fire Owner");
		} else {
			if (employee.boss.firstChild == employee) {
				Node newFirstChild = employee.nextSibling;
				employee.boss.firstChild = newFirstChild;
				employee.boss = null;
				newFirstChild.prevSibling = null;
				employee.nextSibling = null;

				if (newManager.firstChild == null) {
					newManager.firstChild = employee.firstChild;
					Node fchild = employee.firstChild;
					fchild.boss=newManager;
					//fchild.nextSibling.boss=newManager;
					while (fchild!= null) {
						fchild.boss = newManager;
						fchild = fchild.nextSibling;
					}
					employee.firstChild = null;
				} else {
					Node managerFirstChild = newManager.firstChild;
					Node employeeFirstChild = employee.firstChild;
					while (employeeFirstChild.nextSibling != null) {
						employeeFirstChild = employeeFirstChild.nextSibling;
					}
					employeeFirstChild.nextSibling = managerFirstChild;
					managerFirstChild.prevSibling = employeeFirstChild;
					newManager.firstChild = employee.firstChild;
					Node newchildren = newManager.firstChild;
					while (newchildren!= null) {
						newchildren.boss = newManager;
						newchildren=newchildren.nextSibling;
					}
					employee.firstChild = null;
				}

			} else {
				employee.boss = null;
				employee.prevSibling.nextSibling = employee.nextSibling;
				employee.nextSibling.prevSibling = employee.prevSibling;
				employee.nextSibling = null;
				employee.prevSibling = null;

				if (newManager.firstChild == null) {
					newManager.firstChild = employee.firstChild;
					Node fchild = newManager.firstChild;
					while (fchild != null) {
						fchild.boss = newManager;
						fchild = fchild.nextSibling;
					}
					employee.firstChild = null;
				} else {
					Node managerFirstChild = newManager.firstChild;
					Node employeeFirstChild = employee.firstChild;
					while (employeeFirstChild.nextSibling != null) {
						employeeFirstChild = employeeFirstChild.nextSibling;
					}
					employeeFirstChild.nextSibling = managerFirstChild;
					managerFirstChild.prevSibling = employeeFirstChild;
					newManager.firstChild = employee.firstChild;
					Node newchildren = newManager.firstChild;
					while (newchildren != null) {
						newchildren.boss = newManager;
						newchildren = newchildren.nextSibling;
					}
					employee.firstChild = null;
				}
			}
			tree.deleteNode(tree.root, id);
		}
	}

	public int boss(int id) throws IllegalIDException {
		//your implementation
		Node employee = tree.search(tree.root, id);
		if (employee == null) {
			throw new IllegalIDException("Illegal ID");
		} else if (employee == owner) {
			return -1;
		} else {
			return employee.boss.data;
		}
	}

	public int lowestCommonBoss(int id1, int id2) throws IllegalIDException {
		//your implementation
		Node employee1 = tree.search(tree.root, id1);
		Node employee2 = tree.search(tree.root, id2);
		if (employee1 == null || employee2 == null) {
			throw new IllegalIDException("Invalid ID");
		} else if (employee1 == owner || employee2 == owner) {
			return -1;
		} else {
			int level1 = level(id1);
			int level2 = level(id2);
			if (level1 > level2) {
				while (level1 != level2) {
					employee1 = employee1.boss;
					level1--;
				}
				while (employee1.boss != employee2.boss) {
					employee1 = employee1.boss;
					employee2 = employee2.boss;
				}
				return employee1.boss.data;
			} else if (level1 < level2) {
				while (level1 != level2) {
					employee2 = employee2.boss;
					level2--;
				}
				while (employee1.boss != employee2.boss) {
					employee1 = employee1.boss;
					employee2 = employee2.boss;
				}
				return employee1.boss.data;
			} else {
				while (employee1.boss != employee2.boss) {
					employee1 = employee1.boss;
					employee2 = employee2.boss;
				}
				return employee1.boss.data;
			}
		}
	}

	public String toString(int id) throws IllegalIDException {
		//your implementation
		Node node = tree.search(tree.root,id);
		String str=abc(tree.root);
		int level=level(id);
		String ans=id+" ";
		String arr[]=str.split(" ");
		for(int i=level+1;i<=maxLevel(id);i++){
			for(int j=0;j< arr.length;j++){
				if(level(Integer.parseInt(arr[j]))==i && checkbosses((Integer.parseInt(arr[j])),id)){
					ans=ans+Integer.parseInt(arr[j])+" ";
				}
			}
		}
		return ans;


	}

	public String abc(Node nod){
		String ans="";
		if(nod==null){
			return "";
		}
		else{
			if(nod.left!=null){
				ans=ans+abc(nod.left);
			}
			ans=ans+nod.data+" ";
			if(nod.right!=null){
				ans=ans+abc(nod.right);
			}
		}
		return ans;
	}
	public static int read(String s,int i){
		while(Character.isDigit(s.charAt(i))){
			i++;
		}
		return i;
	}
	public int maxLevel(int id) throws IllegalIDException {
		String str=abc(tree.root);
		String arr[]=str.split(" ");
		int level=level(id);

		for(int i=0;i<arr.length;i++){
			if(Integer.parseInt(arr[i])>level){
				level=Integer.parseInt(arr[i]);
			}
		}
		return level;
	}
	public boolean checkbosses(int id1,int id2){
		Node node1=tree.search(tree.root,id1);
		Node node2=tree.search(tree.root,id2);
		while(node1!=null && node1!=node2){
			node1=node1.boss;
		}
		if(node1==node2){
			return true;
		}
		else{
			return false;
		}
	}
}




