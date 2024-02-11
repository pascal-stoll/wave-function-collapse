import java.util.Optional;

public class BinaryTree<E> {

    public E value;
    public Optional<BinaryTree<E>> rightNode;
    public Optional<BinaryTree<E>> leftNode;

    public BinaryTree(final E value, final Optional<BinaryTree<E>> rightNode, final Optional<BinaryTree<E>> leftNode){
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.value = value;
    }
}
