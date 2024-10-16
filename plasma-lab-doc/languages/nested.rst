Nested B-LTL
============

We added a new **nested probability** operator. This operator allows you to check, within a property, 
if a sub-property's probability is superior or equal to a given probability. To do this we extended the B-LTL grammar with a new nested transition::
  
  Property::= 'Pr >=' number '['Property']'