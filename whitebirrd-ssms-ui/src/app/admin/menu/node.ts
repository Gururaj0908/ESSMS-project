export class Node<T> {

   public data: T;
   public children: Array<Node<T>>;

   constructor() {
   }

	/**
	 * Return the children of Node<T>. The Tree<T> is represented by a single root
	 * Node<T> whose children are represented by a List<Node<T>>. Each of these
	 * Node<T> elements in the List can have children. The getChildren() method will
	 * return the children of a Node<T>.
	 * 
	 * @return the children of Node<T>
	 */
   public getChildren(): Array<Node<T>> {
      if (this.children == null) {
         return new Array<Node<T>>();
      }
      return this.children;
   }

	/**
	 * Sets the children of a Node<T> object. See docs for getChildren() for more
	 * information.
	 * 
	 * @param children
	 *            the Array<Node<T>> to set.
	 */
   public setChildren(children: Array<Node<T>>) {
      this.children = children;
   }

	/**
	 * Returns the number of immediate children of this Node<T>.
	 * 
	 * @return the number of immediate children.
	 */
   public getNumberOfChildren(): number {
      if (this.children == null) {
         return 0;
      }
      return this.children.length;
   }

	/**
	 * Adds a child to the list of children for this Node<T>. The addition of the
	 * first child will create a new List<Node<T>>.
	 * 
	 * @param child
	 *            a Node<T> object to set.
	 */
   public addChild(child: Node<T>) {
      if (this.children == null) {
         this.children = new Array<Node<T>>();
      }
      this.children.push(child);
   }

   public getData(): T {
      return this.data;
   }

   public setData(data: T) {
      this.data = data;
   }

}