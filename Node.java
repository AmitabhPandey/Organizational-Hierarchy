public class Node {

        int data;
        int height;
        Node left;
        Node right;
        Node boss;
        Node firstChild;
        Node nextSibling;
        Node prevSibling;



        public Node(int data) {
            this.data = data;
            height = 1;
        }
    }

