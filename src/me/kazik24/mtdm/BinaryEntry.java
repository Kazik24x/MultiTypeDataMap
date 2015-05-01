package me.kazik24.mtdm;

import java.io.InputStream;
import java.io.OutputStream;


/**Interface for data entry, that can be write or read by streams.
 * @author Kazik24
 */
public interface BinaryEntry{
	/**Opens this entry for writing.
	 * @param append if true, {@link OutputStream} will append data to entry. If false,
	 * entry is erased and stream will write new data to it.
	 * @return {@link OutputStream} used for writing operations or null if entry is already open.
	 */
	public OutputStream openWrite(boolean append);
	/**Opens this entry for reading.
	 * @return {@link InputStream} used for reading operations or null if entry is already open.
	 */
	public InputStream openRead();
	/**Returns true if entry is opened by {@link #openRead()} or {@link #openWrite(boolean)}
	 * and stream returned by those methods has not yet been closed.
	 * @return true if entry is opened
	 */
	public boolean isOpened();
	/**Returns true if entry if has been opened by {@link #openRead()}
	 * and stream returned by that methods has not yet been closed.
	 * @return true if entry is opened in read mode.
	 */
	public boolean isReadOpened();
	/**Returns true if entry if has been opened by {@link #openWrite(boolean)}
	 * and stream returned by that methods has not yet been closed.
	 * @return true if entry is opened in write mode.
	 */
	public boolean isWriteOpened();
}
