public class SearchTree {
    private class TreeNode {
        TreeNode left;
        TreeNode right;
        String data;

        public TreeNode(String val) {
            data = val;
            left = null;
            right = null;
        }
    }

    private TreeNode root;

    public SearchTree(String[] data) {
        for (String s : data) {
            root = insert(root, s);
        }
    }

    private TreeNode insert(TreeNode curNode, String s) {
        if (curNode == null) {
            return new TreeNode(s);
        }

        int diff = curNode.data.compareTo(s);

        if (diff > 0) {
            curNode.left = insert(curNode.left, s);
        } else if (diff < 0) {
            curNode.right = insert(curNode.right, s);
        }

        return curNode;
    }

    public void performTraversals() {
        System.out.print("Pre order: ");
        preOrder(root);
        System.out.println();

        System.out.print("In order: ");
        inOrder(root);
        System.out.println();

        System.out.print("Post order: ");
        postOrder(root);
        System.out.println();
    }

    private void preOrder(TreeNode curNode) {
        if (curNode != null) {
            System.out.print(curNode.data + " ");
            preOrder(curNode.left);
            preOrder(curNode.right);
        }
    }

    private void inOrder(TreeNode curNode) {
        if (curNode != null) {
            inOrder(curNode.left);
            System.out.print(curNode.data + " ");
            inOrder(curNode.right);
        }
    }

    private void postOrder(TreeNode curNode) {
        if (curNode != null) {
            postOrder(curNode.left);
            postOrder(curNode.right);
            System.out.print(curNode.data + " ");
        }
    }

    public static void main(String[] args) {
        // Insert data below:
        String data = "42 17 10 50 15 20 12";
        SearchTree newTree = new SearchTree(data.split(" "));
        newTree.performTraversals();
    }
}
