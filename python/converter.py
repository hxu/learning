#!/usr/bin/python


def divmod_long(src, src_base, divisor):
    print "in divmod_long"
    dividend = []
    remainder = 0
    for d in src:
        print "divmod for {} and remainder {}".format(d, remainder)
        (e, remainder) = divmod(d + remainder * src_base, divisor)
        print "returned e of {} and remainder {}".format(e, remainder)
        if dividend or e:
            dividend += [e]
            print "dividend is now {}".format(dividend)
    return (dividend, remainder)


def convert(src, src_base, dst_base):
    result = []
    while src:
        print "source array: {}".format(src)
        (src, remainder) = divmod_long(src, src_base, dst_base)
        print "returned new source {} and remainder {}".format(src, remainder)
        result = [remainder] + result
        print "result is now {} \n".format(result)
    print "done \n"
    return result


if __name__ == "__main__":
    print "{} \n".format(convert([1, 0, 0, 1, 0, 0, 0, 0], 2, 10))
