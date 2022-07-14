public class AVLtree{
        Node root;
    public int size(Node node){
        if(node==null){
            return 0;
        }
        else{

            return 1+size(node.left)+size(node.right);
        }

    }
        public Node search(Node node,int data){
            if(node==null){
                return null;
            }
            if(node.data==data){
                return node;
            }
            else if(data<node.data){
                return search(node.left,data);
            }
            else{
                return search(node.right,data);
            }
        }
        public int height(Node n){
            if(n==null){
                return 0;
            }
            else{
                return 1+Math.max(height(n.left),height(n.right));
            }
        }
        public Node rotateRight(Node z){
            Node y=z.left;
            Node temp=y.right;
            y.right=z;
            z.left=temp;
            y.height=height(y);
            z.height=height(z);
            return y;
        }
        public Node rotateLeft(Node z){
            Node y= z.right;
            Node temp=y.left;
            y.left=z;
            z.right=temp;
            y.height=height(y);
            z.height=height(z);
            return y;
        }


        public Node insert(Node root,int data){
            if(root==null){
                return new Node(data);
            }
            else{
                if(data<root.data){
                    root.left=insert(root.left,data);
                }
                else if(data> root.data){
                    root.right=insert(root.right,data);
                }
                else{
                    return root;
                }


            }
            root.height=height(root);
            if(height(root.left)-height(root.right)>1 && data<root.left.data){
                return rotateRight(root);
            }
            if(height(root.left)-height(root.right)<-1 && data>root.right.data){
                return rotateLeft(root);
            }
            if(height(root.left)-height(root.right)>1 && data>root.left.data){
                root.left=rotateLeft(root.left);
                return rotateRight(root);
            }
            if(height(root.left)-height(root.right)<-1 && data<root.right.data){
                root.right=rotateRight(root.right);
                return rotateLeft(root);
            }
            return root;
        }
        public Node leftMax(Node a){
            a=a.left;
            while(a.right!=null){
                a=a.right;
            }
            return a;
        }
        public Node delete(Node root,int data){
            if(root==null){
                return null;
            }
            else{
                if(data<root.data){
                    root.left=delete(root.left,data);
                }
                else if(data>root.data){
                    root.right=delete(root.right,data);
                }
                else{
                    Node t=root;
                    if(root.left==null && root.right==null){
                        root=null;
                    }
                    else if(root.left!=null && root.right==null){
                        root=root.left;
                    }
                    else if(root.left==null && root.right!=null){
                        root=root.right;
                    }
                    else{
                        Node leftmax=leftMax(root);
                        Node temp=root.right;
                        root.left=delete(root.left,leftmax.data);
                        Node temp2=root.left;
                        root=leftmax;
                        root.right=temp;
                        root.left=temp2;

                    }
                    if(t==this.root){
                        this.root=root;
                    }
                }
            }
            if(root==null){
                return null;
            }
            root.height=height(root);
            if(height(root.left)-height(root.right)>1 && (height(root.left.left)-height(root.left.right)>=0)){
                return rotateRight(root);
            }
            if(height(root.left)-height(root.right)>1 && (height(root.left.left)-height(root.left.right)<0)){
                root.left=rotateLeft(root.left);
                return rotateRight(root);
            }
            if(height(root.left)-height(root.right)<-1 && (height(root.right.left)-height(root.right.right)<=0)){
                return rotateLeft(root);
            }
            if(height(root.left)-height(root.right)<-1 && (height(root.left.left)-height(root.left.right)<0)){
                root.right=rotateRight(root.right);
                return rotateLeft(root);
            }
            return root;
        }
        public void insertNode(Node node,int data){
            root=insert(node,data);
        }
        public void deleteNode(Node node, int data){
            root=delete(node,data);
        }
        public Node parentNode(Node node){
            Node finder= root;
            Node find=root;
            while(finder!=node){
                if(node==null || node ==this.root){
                    return null;
                }
                else if(node.data<finder.data){
                    find=finder;
                    finder=finder.left;
                }
                else if(node.data>finder.data){
                    find=finder;
                    finder=finder.right;
                }
                else{
                    break;
                }
            }
            return find;


        }

    }








