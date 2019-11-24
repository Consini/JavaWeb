
class TreeNode {
 int val = 0;
 TreeNode left = null;
 TreeNode right = null;
 public TreeNode(int val) {
 this.val = val;
 }
 }
public class 镜像二叉树 {
    public void Mirror(TreeNode root) {
        if(root == null || (root.left == null && root.right == null)){
            return ;
        }
        TreeNode node = root.left;
        root.left = root.right;
        root.right = node;
        if(root.left != null){
            Mirror(root.left);
        }
        if(root.right != null){
            Mirror(root.right);
        }
    }
}
