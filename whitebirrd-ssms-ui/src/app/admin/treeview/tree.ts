import { Node } from './node';

export class Tree<T> {

constructor() {

}

private rootElement: Node<T>;

    /**
	 * Return the root Node of the tree.
	 *
	 * @return the root element.
	 */
public getRootElement(): Node<T> {
return this.rootElement;
}

    /**
	 * Set the root Element for the tree.
	 *
	 * @param rootElement
	 *            the root element to set.
	 */
public setRootElement(rootElement: Node<T>) {
this.rootElement = rootElement;
}

    /**
	 * Returns the Tree<T> as a List of Node<T> objects. The elements of the List
	 * are generated from a pre-order traversal of the tree.
	 *
	 * @return a List<Node<T>>.
	 */
public toList(): Array<Node<T>> {
const list: Array<Node<T>> = new Array();
this.walk(this.rootElement, list);
return list;
}

    /**
	 * Returns a String representation of the Tree. The elements are generated from
	 * a pre-order traversal of the Tree.
	 *
	 * @return the String representation of the Tree.
	 */
public toString(): string {
return this.toList().toString();
}

    /**
	 * Walks the Tree in pre-order style. This is a recursive method, and is called
	 * from the toList() method with the root element as the first argument. It
	 * appends to the second argument, which is passed by reference * as it recurses
	 * down the tree.
	 *
	 * @param element
	 *            the starting element.
	 * @param list
	 *            the output of the walk.
	 */
private walk(element: Node<T>, list: Array<Node<T>>) {
list.push(element);
for (const data of element.getChildren()) {
this.walk(data, list);
}
}
}
