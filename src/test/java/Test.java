import jdk.incubator.vector.VectorOperators;

import java.lang.Integer;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Test {

    public static void main(String[] args) {
        // Records and Mutability
        Mutable k = new Mutable(50);
        PseudoImmutable x = new PseudoImmutable("Pascal", k);

        k.setId(30);
        x.id().setId(20);
        System.out.println(x);

        // Open Types
        OpenType<Integer> y = new OpenType<>(50);
        OpenType<String> z = new OpenType<>("Hallo");

        y.setE(30);
        z.setE("Welt");

        Tuple<String> t = new Tuple<String>("Hallo", "Welt");
        System.out.println(t.second() + " " + t.first());

        // Foreach-Loop & Collections
        HashSet<Integer> test = new HashSet<>(List.of(1, 2, 3, 4));
        for (final int i : test) {
            System.out.println(i);
        }

        // Lambdas
        Predicate<Integer> greaterFive = i -> i > 5;
        Function<String, Integer> stringLength = String::length;
        Supplier<String> helloGenerator = () -> "Hallo";
        Function<String, String> concatWorld = str -> str.concat(" Welt");

        System.out.println(greaterFive.test(10));
        System.out.println(stringLength.apply("Hallo Welt"));
        System.out.println(helloGenerator.get());
        System.out.println(concatWorld.apply("Hallo"));

        boolean result = greaterFive.test(stringLength.apply(concatWorld.apply(helloGenerator.get())));
        System.out.println(result);


        // Recursive Data Structures
        Optional<BinaryTree<Integer>> e = Optional.empty();
        BinaryTree<Integer> pascalTriangle = new BinaryTree<>(1, e, e);

            // 2nd layer
        pascalTriangle.rightNode = Optional.of(new BinaryTree<>(1, e, e));
        pascalTriangle.leftNode = Optional.of(new BinaryTree<>(1, e, e));
            // 3rd layer
        pascalTriangle.rightNode.get().rightNode = Optional.of(new BinaryTree<>(1, e, e));
        pascalTriangle.rightNode.get().leftNode = Optional.of(new BinaryTree<>(2, e, e));
        pascalTriangle.leftNode.get().leftNode = Optional.of(new BinaryTree<>(1, e, e));
        pascalTriangle.leftNode.get().rightNode = Optional.of(pascalTriangle.rightNode.get().leftNode.get());

        System.out.println(pascalTriangle);

    }
}
