import java.security.MessageDigest;
import java.util.Arrays;



public class HashContainer {
    
    public byte[] bytes;
    
    public HashContainer(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(bytes);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HashContainer other = (HashContainer) obj;
        if (!Arrays.equals(bytes, other.bytes))
            return false;
        return true;
    }
    
    
    
}
