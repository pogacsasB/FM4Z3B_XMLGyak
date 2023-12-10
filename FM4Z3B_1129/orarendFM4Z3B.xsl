<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">

        <html>
            <body>
                <h2>Orarend</h2>
                <table border = "4">
                    <tr bgcolor="#9acd32">
                        <th>ID</th>
                        <th>Kurzusnev</th>
                        <th>Kredit</th>
                        <th>Hely</th>
                        <th>Idopont</th>
                        <th>Oktato</th>
                    </tr>

                    <xsl:for-each select="FM4Z3B_orarend/kurzus">
                        <tr>
                            <td><xsl:value-of select="@id"/></td>
                            <td><xsl:value-of select="kurzusnev"/></td>
                            <td><xsl:value-of select="kredit"/></td>
                            <td><xsl:value-of select="hely"/></td>
                            <td><xsl:value-of select="idopont"/></td>
                            <td><xsl:value-of select="oktato"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>