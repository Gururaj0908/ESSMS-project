export class Formatter {
   // font weight
   w: number;
   // italics
   i: number;
   // underline
   u: number;
   // striked
   s: number;
   // foreground color
   f: string;
   // background color
   b: string;
   // font family
   ff: string;
   // style object
   styleObj: any = {};
   constructor(d?: Formatter) {
      if (d) {
         this.w = d.w;
         this.i = d.i;
         this.u = d.u;
         this.s = d.s;
         this.f = d.f;
         this.b = d.b;
         this.ff = d.ff;
         this.GenerateStyle();
      }
   }
   private GenerateStyle() {
      if (this.w) {
         this.styleObj.fontWeight = 'bold';
      }
      if (this.i) {
         this.styleObj.fontStyle = 'italic';
      }
      if (this.u || this.s) {
         if (this.u && this.s) {
            this.styleObj.textDecoration = 'underline line-through';
         } else if (this.u && !this.s) {
            this.styleObj.textDecoration = 'underline';
         } else {
            this.styleObj.textDecoration = 'line-through';
         }
      }
      if (this.f) {
         this.styleObj.color = this.f;
      }
      if (this.b) {
         this.styleObj.backgroundColor = this.b;
      }
      if (this.ff) {
         this.styleObj.fontFamily = this.ff;
      }
   }
}
