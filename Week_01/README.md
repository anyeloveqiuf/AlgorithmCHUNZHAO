学习笔记
1.改写Deque
Deque<String> deque = new LinkedList<String>();
deque.addFirst("a");    // 从头加
deque.addLast("b");     // 从尾加
deque.push("c");        // 从头加
deque.addFirst("d");    // 从头加
deque.addLast("e");     // 从尾加
deque.push("f");        // 从尾加
2.分析Queue和Priority Queue
public interface Queue<E> extends Collection<E> {
    // 添加元素，成功返回true，失败报异常
    boolean add(E e);
    // 添加元素，成功返回true，失败返回false
    boolean offer(E e);
    // 取出并删除对头，如果队列空，则报异常
    E remove();
    // 取出并删除对头，如果队列空，则返回null
    E poll();
    // 取出不删除对头，如果队列空，则报异常
    E element();
    // 取出不删除对头，如果队列空，则返回null
    E peek();
}
import java.util.*;
import java.util.function.Consumer;

public class PriorityQueue<E> extends AbstractQueue<E> implements java.io.Serializable {

    // 默认大小
    private static final int DEFAULT_INITIAL_CAPACITY = 11;
    // 队列。底层是平衡二叉树。按照自定义序或者自然序。不允许空。
    // 只保证了头元素是最小的。并不保证有序。
    transient Object[] queue; // transient关键字标识不会被序列化
    // 队列元素个数
    private int size = 0;
    // 自定义比较器。空就使用自然序。
    private final Comparator<? super E> comparator;
    // 队列被结构性修改的次数。
    transient int modCount = 0;
    // 创建初始值为11的自然序队列
    public PriorityQueue() {
        this(DEFAULT_INITIAL_CAPACITY, null);
    }
    // 指定初始值大小
    public PriorityQueue(int initialCapacity) {
        this(initialCapacity, null);
    }
    public PriorityQueue(Comparator<? super E> comparator) {
        this(DEFAULT_INITIAL_CAPACITY, comparator);
    }
    public PriorityQueue(int initialCapacity,
                         Comparator<? super E> comparator) {
        if (initialCapacity < 1)
            throw new IllegalArgumentException();
        this.queue = new Object[initialCapacity];
        this.comparator = comparator;
    }
    public PriorityQueue(Collection<? extends E> c) {
        // 是SortedSet实例，就用SortedSet的比较器
        if (c instanceof SortedSet<?>) {
            SortedSet<? extends E> ss = (SortedSet<? extends E>) c;
            this.comparator = (Comparator<? super E>) ss.comparator();
            initElementsFromCollection(ss);
        }
        // 是PriorityQueue实例，就用PriorityQueue的比较器
        else if (c instanceof PriorityQueue<?>) {
            PriorityQueue<? extends E> pq = (PriorityQueue<? extends E>) c;
            this.comparator = (Comparator<? super E>) pq.comparator();
            initFromPriorityQueue(pq);
        }
        else {
            // 其余情况，没有自定义比较器
            this.comparator = null;
            initFromCollection(c);
        }
    }
    public PriorityQueue(PriorityQueue<? extends E> c) {
        this.comparator = (Comparator<? super E>) c.comparator();
        initFromPriorityQueue(c);
    }
    public PriorityQueue(SortedSet<? extends E> c) {
        this.comparator = (Comparator<? super E>) c.comparator();
        initElementsFromCollection(c);
    }
    private void initFromPriorityQueue(PriorityQueue<? extends E> c) {
        if (c.getClass() == PriorityQueue.class) {
            // 队列转成数组
            this.queue = c.toArray();
            this.size = c.size();
        } else {
            initFromCollection(c);
        }
    }

    private void initElementsFromCollection(Collection<? extends E> c) {
        Object[] a = c.toArray();
        // If c.toArray incorrectly doesn't return Object[], copy it.
        if (a.getClass() != Object[].class)
            a = Arrays.copyOf(a, a.length, Object[].class);
        int len = a.length;
        if (len == 1 || this.comparator != null)
            for (int i = 0; i < len; i++)
                if (a[i] == null)
                    throw new NullPointerException();
        this.queue = a;
        this.size = a.length;
    }
    // 根据给定集合初始值队列数组
    private void initFromCollection(Collection<? extends E> c) {
        initElementsFromCollection(c);
        // 构建最大堆
        heapify();
    }
    // 虚拟机能开辟的最大数组
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    // 数组扩容
    // 时间复杂度O(logN)
    private void grow(int minCapacity) {
        int oldCapacity = queue.length;
        // 不足64。扩容两倍+2。超过64，扩容1.5倍。
        int newCapacity = oldCapacity + ((oldCapacity < 64) ?
                                         (oldCapacity + 2) :
                                         (oldCapacity >> 1));
        // overflow-conscious code
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // 将原来的是数组拷贝到新数组
        queue = Arrays.copyOf(queue, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // 溢出，报OOM错误
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }
    // add底层调的offer
    public boolean add(E e) {
        return offer(e);
    }
    // 判断数组是否需要扩容，需要则调用grow犯法
    // 插入元素后，可能破坏堆的平衡，调用siftUp（上滤）方法调整堆至平衡
    public boolean offer(E e) {
        // 添加元素为空，抛异常
        if (e == null)
            throw new NullPointerException();
        // 修改数+1
        modCount++;
        int i = size;
        // 个数超过数组大小，扩容
        if (i >= queue.length)
            grow(i + 1);
        size = i + 1;
        if (i == 0) // 空数组直接加
            queue[0] = e;
        else
            siftUp(i, e); // i数组大小，e添加元素
        return true;
    }

    public E peek() {
        return (size == 0) ? null : (E) queue[0];
    }

    private int indexOf(Object o) {
        if (o != null) {
            for (int i = 0; i < size; i++)
                if (o.equals(queue[i]))
                    return i;
        }
        return -1;
    }

    public boolean remove(Object o) {
        int i = indexOf(o);
        if (i == -1)
            return false;
        else {
            removeAt(i);
            return true;
        }
    }

    boolean removeEq(Object o) {
        for (int i = 0; i < size; i++) {
            if (o == queue[i]) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    public Object[] toArray() {
        return Arrays.copyOf(queue, size);
    }

    public <T> T[] toArray(T[] a) {
        final int size = this.size;
        if (a.length < size)
            // Make a new array of a's runtime type, but my contents:
            return (T[]) Arrays.copyOf(queue, size, a.getClass());
        System.arraycopy(queue, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    public Iterator<E> iterator() {
        return new Itr();
    }
    // 普通数组的遍历
    // 内部类Itr，利用curosr指针迭代内部数组queue
    private final class Itr implements Iterator<E> {

        private int cursor = 0;

        private int lastRet = -1;

        private ArrayDeque<E> forgetMeNot = null;

        private E lastRetElt = null;

        private int expectedModCount = modCount;

        public boolean hasNext() {
            return cursor < size ||
                (forgetMeNot != null && !forgetMeNot.isEmpty());
        }

        public E next() {
            if (expectedModCount != modCount)
                throw new ConcurrentModificationException();
            if (cursor < size)
                return (E) queue[lastRet = cursor++];
            if (forgetMeNot != null) {
                lastRet = -1;
                lastRetElt = forgetMeNot.poll();
                if (lastRetElt != null)
                    return lastRetElt;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            if (expectedModCount != modCount)
                throw new ConcurrentModificationException();
            if (lastRet != -1) {
                E moved = PriorityQueue.this.removeAt(lastRet);
                lastRet = -1;
                if (moved == null)
                    cursor--;
                else {
                    if (forgetMeNot == null)
                        forgetMeNot = new ArrayDeque<>();
                    forgetMeNot.add(moved);
                }
            } else if (lastRetElt != null) {
                PriorityQueue.this.removeEq(lastRetElt);
                lastRetElt = null;
            } else {
                throw new IllegalStateException();
            }
            expectedModCount = modCount;
        }
    }

    public int size() {
        return size;
    }

    public void clear() {
        modCount++;
        for (int i = 0; i < size; i++)
            queue[i] = null;
        size = 0;
    }

    // 删除
    public E poll() {
        if (size == 0)
            return null;
        int s = --size;
        modCount++;
        E result = (E) queue[0];
        E x = (E) queue[s];
        queue[s] = null;
        if (s != 0)
            siftDown(0, x);
        return result;
    }

    private E removeAt(int i) {
        // assert i >= 0 && i < size;
        modCount++;
        int s = --size;
        if (s == i) // removed last element
            queue[i] = null;
        else {
            E moved = (E) queue[s];
            queue[s] = null;
            // 与下层元素交换
            siftDown(i, moved);
            if (queue[i] == moved) {
                siftUp(i, moved);
                if (queue[i] != moved)
                    return moved;
            }
        }
        return null;
    }
    // 自底向上，不断比较、交换
    private void siftUp(int k, E x) {
        if (comparator != null) // 用自定义比较器
            siftUpUsingComparator(k, x);
        else
            siftUpComparable(k, x); // 用自然序比较器
    }
    // 在数组k的位置插入x
    // 把x赋值给key
    // 不断比较key和k的父节点e（queue[(k-1)/2]）的关系
    // 1、若key<e，则queue[k]=e，k回溯至e
    // 2、若key>=e捉着k已经到达根节点，则结束循环
    // queue[k]=e
    // 时间复杂度O(logn)
    private void siftUpComparable(int k, E x) { // k数组大小，x添加元素
        Comparable<? super E> key = (Comparable<? super E>) x; // 把x赋值给key
        while (k > 0) { // 当k不是根节点
            int parent = (k - 1) >>> 1; // 取出k父节点parent下标
            Object e = queue[parent]; // 取出parent值
            if (key.compareTo((E) e) >= 0) // 比较key>=父节点e。结束循环
                break;
            queue[k] = e; // 赋值操作
            k = parent; // k指针回溯操作
        }
        queue[k] = key;
    }

    private void siftUpUsingComparator(int k, E x) {
        while (k > 0) {
            int parent = (k - 1) >>> 1;
            Object e = queue[parent];
            if (comparator.compare(x, (E) e) >= 0)
                break;
            queue[k] = e;
            k = parent;
        }
        queue[k] = x;
    }

    private void siftDown(int k, E x) {
        if (comparator != null)
            siftDownUsingComparator(k, x);
        else
            siftDownComparable(k, x);
    }

    // 自顶向下
    // 时间复杂度O(logn)
    private void siftDownComparable(int k, E x) {
        Comparable<? super E> key = (Comparable<? super E>)x; // x赋值给key
        int half = size >>> 1;        // 当不是叶子节点时执行循环
        while (k < half) {
            int child = (k << 1) + 1; // 左孩子
            Object c = queue[child]; // 左孩子的值
            int right = child + 1; // 右孩子
            if (right < size &&
                ((Comparable<? super E>) c).compareTo((E) queue[right]) > 0) // 左孩子大于右孩子的情况
                c = queue[child = right]; // right赋值给child,c的值变成右孩子的值
            if (key.compareTo((E) c) <= 0) // 比较key和c的大小
                break;
            queue[k] = c;
            k = child;
        }
        queue[k] = key;
    }

    private void siftDownUsingComparator(int k, E x) {
        int half = size >>> 1;
        while (k < half) {
            int child = (k << 1) + 1;
            Object c = queue[child];
            int right = child + 1;
            if (right < size &&
                comparator.compare((E) c, (E) queue[right]) > 0)
                c = queue[child = right];
            if (comparator.compare(x, (E) c) <= 0)
                break;
            queue[k] = c;
            k = child;
        }
        queue[k] = x;
    }
    // 构建最大堆
    // 把数组看一个未调整完毕的堆
    // 从第一个非叶子节点开始，从下往上、从右往左不断调用siftDown方法
    private void heapify() {
        for (int i = (size >>> 1) - 1; i >= 0; i--)
            siftDown(i, (E) queue[i]);
    }

    public Comparator<? super E> comparator() {
        return comparator;
    }

    private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException {
        // Write out element count, and any hidden stuff
        s.defaultWriteObject();

        // Write out array length, for compatibility with 1.5 version
        s.writeInt(Math.max(2, size + 1));

        // Write out all elements in the "proper order".
        for (int i = 0; i < size; i++)
            s.writeObject(queue[i]);
    }

    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
        // Read in size, and any hidden stuff
        s.defaultReadObject();

        // Read in (and discard) array length
        s.readInt();

        queue = new Object[size];

        // Read in all elements.
        for (int i = 0; i < size; i++)
            queue[i] = s.readObject();

        // Elements are guaranteed to be in "proper order", but the
        // spec has never explained what that might be.
        heapify();
    }

    public final Spliterator<E> spliterator() {
        return new PriorityQueueSpliterator<E>(this, 0, -1, 0);
    }

    static final class PriorityQueueSpliterator<E> implements Spliterator<E> {
        /*
         * This is very similar to ArrayList Spliterator, except for
         * extra null checks.
         */
        private final PriorityQueue<E> pq;
        private int index;            // current index, modified on advance/split
        private int fence;            // -1 until first use
        private int expectedModCount; // initialized when fence set

        /** Creates new spliterator covering the given range */
        PriorityQueueSpliterator(PriorityQueue<E> pq, int origin, int fence,
                             int expectedModCount) {
            this.pq = pq;
            this.index = origin;
            this.fence = fence;
            this.expectedModCount = expectedModCount;
        }

        private int getFence() { // initialize fence to size on first use
            int hi;
            if ((hi = fence) < 0) {
                expectedModCount = pq.modCount;
                hi = fence = pq.size;
            }
            return hi;
        }

        public PriorityQueueSpliterator<E> trySplit() {
            int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
            return (lo >= mid) ? null :
                new PriorityQueueSpliterator<E>(pq, lo, index = mid,
                                                expectedModCount);
        }

        public void forEachRemaining(Consumer<? super E> action) {
            int i, hi, mc; // hoist accesses and checks from loop
            PriorityQueue<E> q; Object[] a;
            if (action == null)
                throw new NullPointerException();
            if ((q = pq) != null && (a = q.queue) != null) {
                if ((hi = fence) < 0) {
                    mc = q.modCount;
                    hi = q.size;
                }
                else
                    mc = expectedModCount;
                if ((i = index) >= 0 && (index = hi) <= a.length) {
                    for (E e;; ++i) {
                        if (i < hi) {
                            if ((e = (E) a[i]) == null) // must be CME
                                break;
                            action.accept(e);
                        }
                        else if (q.modCount != mc)
                            break;
                        else
                            return;
                    }
                }
            }
            throw new ConcurrentModificationException();
        }

        public boolean tryAdvance(Consumer<? super E> action) {
            if (action == null)
                throw new NullPointerException();
            int hi = getFence(), lo = index;
            if (lo >= 0 && lo < hi) {
                index = lo + 1;
                @SuppressWarnings("unchecked") E e = (E)pq.queue[lo];
                if (e == null)
                    throw new ConcurrentModificationException();
                action.accept(e);
                if (pq.modCount != expectedModCount)
                    throw new ConcurrentModificationException();
                return true;
            }
            return false;
        }

        public long estimateSize() {
            return (long) (getFence() - index);
        }

        public int characteristics() {
            return Spliterator.SIZED | Spliterator.SUBSIZED | Spliterator.NONNULL;
        }
    }
}
