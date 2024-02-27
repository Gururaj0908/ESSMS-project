import { OperationType } from './OperationType';
export class ExpressionNode {
   public isConstant: boolean;
   public lhs: ExpressionNode;
   public rhs: ExpressionNode;
   public operation: OperationType;
   public value: number;
   public key: string;
   public isArray: boolean;
   constructor(e?: ExpressionNode) {
      if (e) {
         this.isArray = e.isArray;
         this.isConstant = e.isConstant;
         if (e.lhs) {
            this.lhs = new ExpressionNode(e.lhs);
         }
         if (e.rhs) {
            this.rhs = new ExpressionNode(e.rhs);
         }
         this.value = e.value;
         this.key = e.key;
         if (typeof e.operation === 'string') {
            this.operation = (<any>OperationType)[e.operation];
         } else {
            this.operation = e.operation;
         }
      }
   }
}
