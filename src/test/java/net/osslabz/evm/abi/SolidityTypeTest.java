package net.osslabz.evm.abi;

import org.junit.jupiter.api.Test;
import net.osslabz.evm.abi.definition.SolidityType;

public class SolidityTypeTest {

    @Test
    public void testBooleanDecodingWithHexStrings() {
        // Arrange: Provide inputs as hex strings
        byte[] hexForTrue = hexToBytes("0x0000000000000000000000000000000000000000000000000000000000000001");
        byte[] hexForFalse = hexToBytes("0x0000000000000000000000000000000000000000000000000000000000000000");
        byte[] hexForBrokenTrue = hexToBytes("0x0100000000000000000000000000000000000000000000000000000000000000");

        // Act: Decode both values
        boolean decodedTrue = decodeBoolean(hexForTrue);
        boolean decodedFalse = decodeBoolean(hexForFalse);
        boolean decodedBrokenTrue = decodeBoolean(hexForBrokenTrue);

        // Assert: Verify that the decoded values are correct
        assert decodedTrue : "Expected true but decoded value is false.";
        assert !decodedFalse : "Expected false but decoded value is true.";
        assert decodedBrokenTrue : "Expected true for reversed 1 int but decoded value is false.";
    }

    private boolean decodeBoolean(byte[] encoded) {
        return (boolean) new SolidityType.BoolType().decode(encoded);
    }

    private byte[] hexToBytes(String hex) {
        // Convert a hex string (with or without the "0x" prefix) to a byte array
        if (hex.startsWith("0x")) {
            hex = hex.substring(2);
        }
        int length = hex.length();
        byte[] bytes = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return bytes;
    }
}
