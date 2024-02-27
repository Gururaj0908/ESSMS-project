import { OperationType } from './OperationType';
import { ExpressionNode } from './ExpressionNode';
import { FormGroup, FormArray } from '@angular/forms';

/**
 * Solves an expression denoted by Expression Node
 */
export class ExpressionSolver {

      /**
       * Constructor call will initiate the object to solve expression. Do pass the FormGroup from we need to take the value
       * @param Form the form group of the control to which the expression is attached to
       */
      constructor(private Form: FormGroup) {
      }

      /**
       * Solves the given expression by traversing through its nodes
       * @param exp the expression
       */
      public SolveExpression(exp: ExpressionNode): ExpressionNode {
            // check if the expression is a constant
            if (exp.isConstant) {
                  return exp;
            } else if (exp.isArray) {
                  // so this expression is an array. Lets solve this array
                  return this.ArraySum(exp);
            } else if (!exp.lhs && !exp.rhs) {
                  let val = 0;
                  // if so then fetch the value from the form group using the control's key and set it inside the expression value
                  // If the key is missing from the form then treat value as 0
                  if (!this.Form.get(exp.key)) {
                        val = 0;
                  } else {
                        val = this.Form.get(exp.key).value;
                  }
                  exp.value = (val == null ? 0 : Number(val));
                  // and return the expression as it doesn't need to be solved
                  return exp;
            }
            // check if the LHS is a constant
            if (!exp.lhs.value) {
                  // if not then solve the LHS first
                  exp.lhs = this.SolveExpression(exp.lhs);
            }
            // check if the RHS is a constant
            if (!exp.rhs.value) {
                  // if not then solve the RHS first
                  exp.rhs = this.SolveExpression(exp.rhs);
            }
            // so we have our operands ready to be operated. Lets operate
            switch (exp.operation) {
                  case OperationType.Add:
                        return this.Add(exp);
                  case OperationType.Minus:
                        return this.Subtract(exp);
                  case OperationType.Multiply:
                        return this.Product(exp);
                  case OperationType.Divide:
                        return this.Divide(exp);
                  default:
                        console.error('Could not understand the operation');
                        return exp;
            }
      }

      public ClearValue(exp: ExpressionNode) {
            if (!exp.isConstant) {
                  exp.value = null;
            }
            if (exp.lhs) {
                  this.ClearValue(exp.lhs);
            }
            if (exp.rhs) {
                  this.ClearValue(exp.rhs);
            }
      }

      private Add(exp: ExpressionNode): ExpressionNode {
            exp.value = exp.lhs.value + exp.rhs.value;
            return exp;
      }

      private Subtract(exp: ExpressionNode): ExpressionNode {
            exp.value = exp.lhs.value - exp.rhs.value;
            return exp;
      }

      private Product(exp: ExpressionNode): ExpressionNode {
            exp.value = exp.lhs.value * exp.rhs.value;
            return exp;
      }

      private Divide(exp: ExpressionNode): ExpressionNode {
            exp.value = exp.lhs.value / exp.rhs.value;
            return exp;
      }

      private ArraySum(exp: ExpressionNode): ExpressionNode {
            const key = exp.key;
            const array = key.substring(0, key.lastIndexOf('.'));
            const member = key.substring(key.lastIndexOf('.') + 1, key.length);
            let ArrayForm: FormArray = this.Form.get(array) as FormArray;
            if (ArrayForm == null) {
                  ArrayForm = this.Form.parent.get(array) as FormArray;
            }
            let finalVal = 0;
            ArrayForm.controls.forEach(grp => {
                  const g = grp as FormGroup;
                  const val = g.get(member).value;
                  finalVal += Number(val);
            });
            exp.value = finalVal;
            return exp;
      }
}
