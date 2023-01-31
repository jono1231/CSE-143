public class TreeTraversals {
    private class TreeNode {
        TreeNode left;
        TreeNode right;
        int data;

        public TreeNode(int data) {
            this.data = data;
            left = null;
            right = null;
        }
    }

    // Tree stored in the binary heap format. Nonexistant nodes denoted by -1s.
    public static int[] heap = new int[] { -1, 4, 2, 1, 7, 3, 8, 5, -1, 9, -1, 6, -1, -1, 0, -1 };
    private TreeNode root;

    public TreeTraversals(int[] heap) {
        root = createTree(root, heap, 1);
    }

    private TreeNode createTree(TreeNode curNode, int[] heap, int index) {
        if (index >= heap.length) {
            return curNode;
        } else if (heap[index] == -1) {
            return curNode;
        }
        if (curNode == null) {
            curNode = new TreeNode(heap[index]);
        }

        curNode.left = createTree(curNode.left, heap, index * 2);
        curNode.right = createTree(curNode.right, heap, index * 2 + 1);

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
        TreeTraversals newTrav = new TreeTraversals(heap);
        newTrav.performTraversals();
    }

}