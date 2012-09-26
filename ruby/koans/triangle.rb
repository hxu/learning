# Triangle Project Code.

# Triangle analyzes the lengths of the sides of a triangle
# (represented by a, b and c) and returns the type of triangle.
#
# It returns:
#   :equilateral  if all sides are equal
#   :isosceles    if exactly 2 sides are equal
#   :scalene      if no sides are equal
#
# The tests for this method can be found in
#   about_triangle_project.rb
# and
#   about_triangle_project_2.rb
#
def triangle(a, b, c)
    sides = [a, b, c]
    unique = sides.uniq
    runsum = 0
    for x in sides
        if x <= 0
            raise TriangleError, "Sides must be >0"
        end
    end

    sides.sort!
    sum = sides.shift + sides.shift
    if sum <= sides[0]
        raise TriangleError
    end

    if unique.length == 3
        return :scalene
    elsif unique.length == 2
        return :isosceles
    elsif unique.length == 1
        return :equilateral
    else
        return nil
    end
end

# Error class used in part 2.  No need to change this code.
class TriangleError < StandardError
end
