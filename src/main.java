import java.util.Set;

public class main {

	public static void main(String[] args) {
        APriori aPriori = new APriori(3);
        aPriori.addBasket("Cat and dog bites");
        aPriori.addBasket("Yahoo news claims a cat mated with a dog and produced viable offspring");
        aPriori.addBasket("Cat killer likely is a big dog");
        aPriori.addBasket("Professional free advice on dog training puppy training");
        aPriori.addBasket("Cat and kitten training and behavior");
        aPriori.addBasket("Dog & Cat provides dog training in Eugene Oregon");
        aPriori.addBasket("Dog and cat is a slang term used by police officers for a male female relationship");
        aPriori.addBasket("Shop for your show dog grooming and pet supplies");

        Set<StringSet> frequentSets = aPriori.getFrequentSets(3);
        System.out.println(frequentSets);

        PCY pcy = new PCY(3, 1024);
        pcy.addBasket("Cat and dog bites");
        pcy.addBasket("Yahoo news claims a cat mated with a dog and produced viable offspring");
        pcy.addBasket("Cat killer likely is a big dog");
        pcy.addBasket("Professional free advice on dog training puppy training");
        pcy.addBasket("Cat and kitten training and behavior");
        pcy.addBasket("Dog & Cat provides dog training in Eugene Oregon");
        pcy.addBasket("Dog and cat is a slang term used by police officers for a male female relationship");
        pcy.addBasket("Shop for your show dog grooming and pet supplies");
        Set<StringSet> frequentSets1 = pcy.getFrequentSets(3);
        System.out.println(frequentSets1);

    }

}
